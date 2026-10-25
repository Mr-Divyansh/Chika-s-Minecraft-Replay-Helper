/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.librealsense2.global.realsense2
 *  org.bytedeco.librealsense2.presets.realsense2
 *  org.bytedeco.librealsense2.rs2_config
 *  org.bytedeco.librealsense2.rs2_context
 *  org.bytedeco.librealsense2.rs2_device
 *  org.bytedeco.librealsense2.rs2_device_list
 *  org.bytedeco.librealsense2.rs2_error
 *  org.bytedeco.librealsense2.rs2_frame
 *  org.bytedeco.librealsense2.rs2_options
 *  org.bytedeco.librealsense2.rs2_pipeline
 *  org.bytedeco.librealsense2.rs2_pipeline_profile
 *  org.bytedeco.librealsense2.rs2_sensor
 *  org.bytedeco.librealsense2.rs2_sensor_list
 *  org.bytedeco.librealsense2.rs2_stream_profile
 *  org.bytedeco.librealsense2.rs2_stream_profile_list
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.IplImage
 *  org.bytedeco.opencv.opencv_core.Size
 */
package org.bytedeco.javacv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.librealsense2.global.realsense2;
import org.bytedeco.librealsense2.rs2_config;
import org.bytedeco.librealsense2.rs2_context;
import org.bytedeco.librealsense2.rs2_device;
import org.bytedeco.librealsense2.rs2_device_list;
import org.bytedeco.librealsense2.rs2_error;
import org.bytedeco.librealsense2.rs2_frame;
import org.bytedeco.librealsense2.rs2_options;
import org.bytedeco.librealsense2.rs2_pipeline;
import org.bytedeco.librealsense2.rs2_pipeline_profile;
import org.bytedeco.librealsense2.rs2_sensor;
import org.bytedeco.librealsense2.rs2_sensor_list;
import org.bytedeco.librealsense2.rs2_stream_profile;
import org.bytedeco.librealsense2.rs2_stream_profile_list;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Size;

public class RealSense2FrameGrabber
extends FrameGrabber {
    private static FrameGrabber.Exception loadingException = null;
    private static final int defaultFrameRate = 30;
    private static final int defaultWidth = 640;
    private static final int defaultHeight = 480;
    private rs2_error error = new rs2_error();
    private rs2_context context;
    private rs2_device device;
    private rs2_pipeline pipeline;
    private rs2_config config;
    private rs2_pipeline_profile pipelineProfile;
    private rs2_frame frameset;
    private int deviceNumber;
    private List<RealSenseStream> streams = new ArrayList<RealSenseStream>();
    private FrameConverter converter = new OpenCVFrameConverter.ToIplImage();

    public static void tryLoad() throws FrameGrabber.Exception {
        if (loadingException != null) {
            loadingException.printStackTrace();
            throw loadingException;
        }
        try {
            Loader.load(org.bytedeco.librealsense2.presets.realsense2.class);
            System.out.println("RealSense2 devices found: " + RealSense2FrameGrabber.getDeviceDescriptions().length);
        }
        catch (Throwable t) {
            loadingException = new FrameGrabber.Exception("Failed to load " + RealSense2FrameGrabber.class, t);
            throw loadingException;
        }
    }

    public RealSense2FrameGrabber() throws FrameGrabber.Exception {
        this(0);
    }

    public RealSense2FrameGrabber(int deviceNumber) throws FrameGrabber.Exception {
        this.deviceNumber = deviceNumber;
        this.context = this.createContext();
    }

    public List<RealSense2DeviceInfo> getDeviceInfos() throws FrameGrabber.Exception {
        ArrayList<RealSense2DeviceInfo> devices = new ArrayList<RealSense2DeviceInfo>();
        rs2_device_list deviceList = this.createDeviceList();
        int count = realsense2.rs2_get_device_count((rs2_device_list)deviceList, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        for (int i = 0; i < count; ++i) {
            rs2_device device = this.createDevice(deviceList, i);
            devices.add(new RealSense2DeviceInfo(this.getDeviceInfo(device, 0), this.getDeviceInfo(device, 1), this.getDeviceInfo(device, 2), RealSense2FrameGrabber.toBoolean(this.getDeviceInfo(device, 6)), RealSense2FrameGrabber.toBoolean(this.getDeviceInfo(device, 8))));
            realsense2.rs2_delete_device((rs2_device)device);
        }
        realsense2.rs2_delete_device_list((rs2_device_list)deviceList);
        return devices;
    }

    public static String[] getDeviceDescriptions() throws FrameGrabber.Exception {
        RealSense2FrameGrabber rs2 = new RealSense2FrameGrabber();
        List<RealSense2DeviceInfo> infos = rs2.getDeviceInfos();
        rs2.release();
        String[] deviceDescriptions = new String[infos.size()];
        for (int i = 0; i < deviceDescriptions.length; ++i) {
            RealSense2DeviceInfo info = infos.get(i);
            deviceDescriptions[i] = info.toString();
        }
        return deviceDescriptions;
    }

    public void disableAllStreams() {
        this.streams.clear();
    }

    public List<RealSenseStream> getEnabledStreams() {
        return this.streams;
    }

    public void enableStream(RealSenseStream stream) {
        this.streams.add(stream);
    }

    public void enableColorStream(int width, int height, int frameRate) {
        this.enableStream(new RealSenseStream(2, 0, new Size(width, height), frameRate, 6));
    }

    public void enableDepthStream(int width, int height, int frameRate) {
        this.enableStream(new RealSenseStream(1, 0, new Size(width, height), frameRate, 1));
    }

    public void enableIRStream(int width, int height, int frameRate, int index) {
        this.enableStream(new RealSenseStream(3, index, new Size(width, height), frameRate, 9));
    }

    public void enableIRStream(int width, int height, int frameRate) {
        this.enableIRStream(width, height, frameRate, 1);
    }

    public void open() throws FrameGrabber.Exception {
        if (this.getDeviceCount() <= 0) {
            throw new FrameGrabber.Exception("No realsense2 device is connected.");
        }
        rs2_device_list devices = this.createDeviceList();
        this.device = this.createDevice(devices, this.deviceNumber);
        realsense2.rs2_delete_device_list((rs2_device_list)devices);
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        if (this.device == null) {
            this.open();
        }
        this.pipeline = this.createPipeline();
        this.config = this.createConfig();
        if (this.streams.isEmpty()) {
            this.enableAllVideoStreams();
            Collections.sort(this.streams);
        }
        for (RealSenseStream stream : this.streams) {
            realsense2.rs2_config_enable_stream((rs2_config)this.config, (int)stream.type, (int)stream.index, (int)stream.size.width(), (int)stream.size.height(), (int)stream.format, (int)stream.frameRate, (rs2_error)this.error);
            RealSense2FrameGrabber.checkError(this.error);
        }
        RealSenseStream largestStream = this.getLargestStreamByArea();
        this.imageWidth = largestStream.size.width();
        this.imageHeight = largestStream.size.height();
        this.pipelineProfile = realsense2.rs2_pipeline_start_with_config((rs2_pipeline)this.pipeline, (rs2_config)this.config, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        realsense2.rs2_pipeline_stop((rs2_pipeline)this.pipeline, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        realsense2.rs2_release_frame((rs2_frame)this.frameset);
        realsense2.rs2_delete_pipeline_profile((rs2_pipeline_profile)this.pipelineProfile);
        realsense2.rs2_delete_config((rs2_config)this.config);
        realsense2.rs2_delete_pipeline((rs2_pipeline)this.pipeline);
        realsense2.rs2_delete_device((rs2_device)this.device);
        this.device = null;
    }

    private void readNextFrameSet() throws FrameGrabber.Exception {
        realsense2.rs2_release_frame((rs2_frame)this.frameset);
        this.frameset = realsense2.rs2_pipeline_wait_for_frames((rs2_pipeline)this.pipeline, (int)15000, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
    }

    @Override
    public void trigger() throws FrameGrabber.Exception {
        if (!this.triggerMode) {
            this.triggerMode = true;
        }
        this.readNextFrameSet();
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        int videoStreamId = Math.max(0, this.videoStream);
        RealSenseStream stream = this.streams.get(videoStreamId);
        switch (stream.type) {
            case 1: {
                return this.grabDepth();
            }
            case 3: {
                return this.grabIR();
            }
        }
        return this.grabColor();
    }

    public Frame grab(int streamType, int streamIndex, int iplDepth, int channels) throws FrameGrabber.Exception {
        if (!this.triggerMode) {
            this.readNextFrameSet();
        }
        return this.grabCVFrame(streamType, streamIndex, iplDepth, channels);
    }

    public float getDistance(int x, int y) throws FrameGrabber.Exception {
        rs2_frame frame = this.findFrameByStreamType(this.frameset, 1, 0);
        if (frame == null) {
            return -1.0f;
        }
        float distance = realsense2.rs2_depth_frame_get_distance((rs2_frame)frame, (int)x, (int)y, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        realsense2.rs2_release_frame((rs2_frame)frame);
        return distance;
    }

    public Frame grabColor() throws FrameGrabber.Exception {
        if (!this.triggerMode) {
            this.readNextFrameSet();
        }
        return this.grabCVFrame(2, 0, 8, 3);
    }

    public Frame grabDepth() throws FrameGrabber.Exception {
        if (!this.triggerMode) {
            this.readNextFrameSet();
        }
        return this.grabCVFrame(1, 0, 16, 1);
    }

    public Frame grabIR() throws FrameGrabber.Exception {
        return this.grabIR(0);
    }

    public Frame grabIR(int streamIndex) throws FrameGrabber.Exception {
        if (!this.triggerMode) {
            this.readNextFrameSet();
        }
        return this.grabCVFrame(3, streamIndex, 8, 1);
    }

    private RealSenseStream getLargestStreamByArea() {
        RealSenseStream largest = this.streams.get(0);
        for (RealSenseStream rs : this.streams) {
            if (rs.size.area() <= largest.size.area()) continue;
            largest = rs;
        }
        return largest;
    }

    private Frame grabCVFrame(int streamType, int streamIndex, int iplDepth, int iplChannels) throws FrameGrabber.Exception {
        rs2_frame frame = this.findFrameByStreamType(this.frameset, streamType, streamIndex);
        if (frame == null) {
            return null;
        }
        Pointer frameData = this.getFrameData(frame);
        Size size = this.getFrameSize(frame);
        IplImage image = IplImage.createHeader((int)size.width(), (int)size.height(), (int)iplDepth, (int)iplChannels);
        opencv_core.cvSetData((CvArr)image, (Pointer)frameData, (int)(size.width() * iplChannels * iplDepth / 8));
        Frame outputFrame = this.converter.convert(image);
        double timestamp = this.getFrameTimeStamp(frame);
        outputFrame.timestamp = Math.round(timestamp);
        realsense2.rs2_release_frame((rs2_frame)frame);
        return outputFrame;
    }

    private rs2_frame findFrameByStreamType(rs2_frame frameset, int streamType, int index) throws FrameGrabber.Exception {
        rs2_frame result = null;
        int frameCount = realsense2.rs2_embedded_frames_count((rs2_frame)frameset, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        int searchIndex = 0;
        for (int i = 0; i < frameCount; ++i) {
            rs2_frame frame = realsense2.rs2_extract_frame((rs2_frame)frameset, (int)i, (rs2_error)this.error);
            RealSense2FrameGrabber.checkError(this.error);
            rs2_stream_profile streamProfile = this.getStreamProfile(frame);
            StreamProfileData streamProfileData = this.getStreamProfileData(streamProfile);
            if (streamType == streamProfileData.nativeStreamIndex.get()) {
                if (searchIndex == index) {
                    result = frame;
                    break;
                }
                ++searchIndex;
            }
            realsense2.rs2_release_frame((rs2_frame)frame);
        }
        return result;
    }

    @Override
    public void release() {
        realsense2.rs2_delete_device((rs2_device)this.device);
        realsense2.rs2_delete_context((rs2_context)this.context);
    }

    public void setSensorOption(Rs2SensorType sensorType, int optionIndex, boolean value) throws FrameGrabber.Exception {
        this.setSensorOption(sensorType, optionIndex, value ? 1.0f : 0.0f);
    }

    public void setSensorOption(Rs2SensorType sensorType, int optionIndex, float value) throws FrameGrabber.Exception {
        rs2_sensor[] sensors;
        for (rs2_sensor sensor : sensors = this.getSensors(this.device)) {
            RealSense2FrameGrabber.checkError(this.error);
            String name = this.getSensorInfo(sensor, 0);
            if (sensorType.getName().equals(name)) {
                rs2_options options = new rs2_options((Pointer)sensor);
                this.setRs2Option(options, optionIndex, value);
            }
            realsense2.rs2_delete_sensor((rs2_sensor)sensor);
        }
    }

    private rs2_context createContext() throws FrameGrabber.Exception {
        rs2_context context = realsense2.rs2_create_context((int)25301, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        return context;
    }

    private rs2_device_list createDeviceList() throws FrameGrabber.Exception {
        rs2_device_list deviceList = realsense2.rs2_query_devices((rs2_context)this.context, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        return deviceList;
    }

    private rs2_device createDevice(rs2_device_list deviceList, int index) throws FrameGrabber.Exception {
        rs2_device device = realsense2.rs2_create_device((rs2_device_list)deviceList, (int)index, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        return device;
    }

    private rs2_pipeline createPipeline() throws FrameGrabber.Exception {
        rs2_pipeline pipeline = realsense2.rs2_create_pipeline((rs2_context)this.context, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        return pipeline;
    }

    private rs2_config createConfig() throws FrameGrabber.Exception {
        rs2_config config = realsense2.rs2_create_config((rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        return config;
    }

    private double getFrameTimeStamp(rs2_frame frame) throws FrameGrabber.Exception {
        double timestamp = realsense2.rs2_get_frame_timestamp((rs2_frame)frame, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        return timestamp;
    }

    private int getDeviceCount() throws FrameGrabber.Exception {
        rs2_device_list deviceList = this.createDeviceList();
        int count = realsense2.rs2_get_device_count((rs2_device_list)deviceList, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        realsense2.rs2_delete_device_list((rs2_device_list)deviceList);
        return count;
    }

    private String getDeviceInfo(rs2_device device, int info) throws FrameGrabber.Exception {
        rs2_error error = new rs2_error();
        boolean isSupported = RealSense2FrameGrabber.toBoolean(realsense2.rs2_supports_device_info((rs2_device)device, (int)info, (rs2_error)error));
        RealSense2FrameGrabber.checkError(error);
        if (!isSupported) {
            return null;
        }
        String infoText = realsense2.rs2_get_device_info((rs2_device)device, (int)info, (rs2_error)error).getString();
        RealSense2FrameGrabber.checkError(error);
        return infoText;
    }

    private String getSensorInfo(rs2_sensor sensor, int info) throws FrameGrabber.Exception {
        rs2_error error = new rs2_error();
        boolean isSupported = RealSense2FrameGrabber.toBoolean(realsense2.rs2_supports_sensor_info((rs2_sensor)sensor, (int)info, (rs2_error)error));
        RealSense2FrameGrabber.checkError(error);
        if (!isSupported) {
            return null;
        }
        String infoText = realsense2.rs2_get_sensor_info((rs2_sensor)sensor, (int)info, (rs2_error)error).getString();
        RealSense2FrameGrabber.checkError(error);
        return infoText;
    }

    private Pointer getFrameData(rs2_frame frame) throws FrameGrabber.Exception {
        Pointer frameData = realsense2.rs2_get_frame_data((rs2_frame)frame, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        return frameData;
    }

    private Size getFrameSize(rs2_frame frame) throws FrameGrabber.Exception {
        int width = realsense2.rs2_get_frame_width((rs2_frame)frame, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        int height = realsense2.rs2_get_frame_height((rs2_frame)frame, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        return new Size(width, height);
    }

    private rs2_stream_profile getStreamProfile(rs2_frame frame) throws FrameGrabber.Exception {
        rs2_stream_profile streamProfile = realsense2.rs2_get_frame_stream_profile((rs2_frame)frame, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        return streamProfile;
    }

    private StreamProfileData getStreamProfileData(rs2_stream_profile streamProfile) throws FrameGrabber.Exception {
        StreamProfileData profileData = new StreamProfileData();
        if (this.isStreamProfile(streamProfile, 15)) {
            VideoStreamProfileData videoStreamProfileData = new VideoStreamProfileData();
            realsense2.rs2_get_video_stream_resolution((rs2_stream_profile)streamProfile, (IntPointer)videoStreamProfileData.width, (IntPointer)videoStreamProfileData.height, (rs2_error)this.error);
            RealSense2FrameGrabber.checkError(this.error);
            profileData = videoStreamProfileData;
        }
        realsense2.rs2_get_stream_profile_data((rs2_stream_profile)streamProfile, (IntPointer)profileData.nativeStreamIndex, (IntPointer)profileData.nativeFormatIndex, (IntPointer)profileData.index, (IntPointer)profileData.uniqueId, (IntPointer)profileData.frameRate, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        return profileData;
    }

    private boolean isSensorExtendableTo(rs2_sensor sensor, int extension) throws FrameGrabber.Exception {
        boolean isExtandable = RealSense2FrameGrabber.toBoolean(realsense2.rs2_is_sensor_extendable_to((rs2_sensor)sensor, (int)extension, (rs2_error)this.error));
        RealSense2FrameGrabber.checkError(this.error);
        return isExtandable;
    }

    private boolean isStreamProfile(rs2_stream_profile profile, int type) throws FrameGrabber.Exception {
        boolean isOfType = RealSense2FrameGrabber.toBoolean(realsense2.rs2_stream_profile_is((rs2_stream_profile)profile, (int)type, (rs2_error)this.error));
        RealSense2FrameGrabber.checkError(this.error);
        return isOfType;
    }

    private boolean matchesVideoStreamProfile(rs2_stream_profile profile, int streamType, int streamFormat, int frameRate, int width, int height) throws FrameGrabber.Exception {
        VideoStreamProfileData data = (VideoStreamProfileData)this.getStreamProfileData(profile);
        return streamType == data.nativeStreamIndex.get() && streamFormat == data.nativeFormatIndex.get() && frameRate == data.frameRate.get() && width == data.width.get() && height == data.height.get();
    }

    private void setRs2Option(rs2_options options, int optionIndex, float value) throws FrameGrabber.Exception {
        boolean isSupported = RealSense2FrameGrabber.toBoolean(realsense2.rs2_supports_option((rs2_options)options, (int)optionIndex, (rs2_error)this.error));
        RealSense2FrameGrabber.checkError(this.error);
        if (!isSupported) {
            throw new FrameGrabber.Exception("Option " + optionIndex + " is not supported!");
        }
        realsense2.rs2_set_option((rs2_options)options, (int)optionIndex, (float)value, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
    }

    private rs2_sensor[] getSensors(rs2_device device) throws FrameGrabber.Exception {
        rs2_sensor_list sensorList = realsense2.rs2_query_sensors((rs2_device)device, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        int sensorCount = realsense2.rs2_get_sensors_count((rs2_sensor_list)sensorList, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        rs2_sensor[] sensors = new rs2_sensor[sensorCount];
        for (int i = 0; i < sensorCount; ++i) {
            rs2_sensor sensor = realsense2.rs2_create_sensor((rs2_sensor_list)sensorList, (int)i, (rs2_error)this.error);
            RealSense2FrameGrabber.checkError(this.error);
            sensors[i] = sensor;
        }
        realsense2.rs2_delete_sensor_list((rs2_sensor_list)sensorList);
        return sensors;
    }

    private rs2_stream_profile[] getStreamProfiles(rs2_sensor sensor) throws FrameGrabber.Exception {
        rs2_stream_profile_list streamList = realsense2.rs2_get_stream_profiles((rs2_sensor)sensor, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        int streamProfileCount = realsense2.rs2_get_stream_profiles_count((rs2_stream_profile_list)streamList, (rs2_error)this.error);
        RealSense2FrameGrabber.checkError(this.error);
        rs2_stream_profile[] profiles = new rs2_stream_profile[streamProfileCount];
        for (int i = 0; i < streamProfileCount; ++i) {
            rs2_stream_profile profile = realsense2.rs2_get_stream_profile((rs2_stream_profile_list)streamList, (int)i, (rs2_error)this.error);
            RealSense2FrameGrabber.checkError(this.error);
            profiles[i] = profile;
        }
        realsense2.rs2_delete_stream_profiles_list((rs2_stream_profile_list)streamList);
        return profiles;
    }

    private void enableAllVideoStreams() throws FrameGrabber.Exception {
        for (rs2_sensor sensor : this.getSensors(this.device)) {
            if (!this.isSensorExtendableTo(sensor, 5)) {
                realsense2.rs2_delete_sensor((rs2_sensor)sensor);
                continue;
            }
            for (rs2_stream_profile profile : this.getStreamProfiles(sensor)) {
                int rsHeight;
                int rsFrameRate = this.frameRate > 0.0 ? (int)this.frameRate : 30;
                int rsWidth = this.imageWidth > 0 ? this.imageWidth : 640;
                int n = rsHeight = this.imageWidth > 0 ? this.imageWidth : 480;
                if (this.matchesVideoStreamProfile(profile, 1, 1, rsFrameRate, rsWidth, rsHeight)) {
                    this.enableDepthStream(this.imageWidth, this.imageHeight, rsFrameRate);
                    continue;
                }
                if (this.matchesVideoStreamProfile(profile, 2, 5, rsFrameRate, rsWidth, rsHeight)) {
                    this.enableColorStream(this.imageWidth, this.imageHeight, rsFrameRate);
                    continue;
                }
                if (!this.matchesVideoStreamProfile(profile, 3, 9, rsFrameRate, rsWidth, rsHeight)) continue;
                this.enableIRStream(this.imageWidth, this.imageHeight, rsFrameRate);
            }
            realsense2.rs2_delete_sensor((rs2_sensor)sensor);
        }
    }

    private static void checkError(rs2_error e) throws FrameGrabber.Exception {
        if (!e.isNull()) {
            throw new FrameGrabber.Exception(String.format("rs_error was raised when calling %s(%s):\n%s\n", realsense2.rs2_get_failed_function((rs2_error)e).getString(), realsense2.rs2_get_failed_args((rs2_error)e).getString(), realsense2.rs2_get_error_message((rs2_error)e).getString()));
        }
    }

    private static boolean toBoolean(int value) {
        return value >= 1;
    }

    private static boolean toBoolean(String value) {
        if (value == null) {
            return false;
        }
        return value.equals("YES");
    }

    public static enum Rs2SensorType {
        StereoModule("Stereo Module"),
        RGBCamera("RGB Camera");

        private String name;

        private Rs2SensorType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public static class RealSense2DeviceInfo {
        private String name;
        private String serialNumber;
        private String firmware;
        private boolean inAdvancedMode;
        private boolean locked;

        RealSense2DeviceInfo(String name, String serialNumber, String firmware, boolean inAdvancedMode, boolean locked) {
            this.name = name;
            this.serialNumber = serialNumber;
            this.firmware = firmware;
            this.inAdvancedMode = inAdvancedMode;
            this.locked = locked;
        }

        public String getName() {
            return this.name;
        }

        public String getSerialNumber() {
            return this.serialNumber;
        }

        public String getFirmware() {
            return this.firmware;
        }

        public boolean isInAdvancedMode() {
            return this.inAdvancedMode;
        }

        public boolean isLocked() {
            return this.locked;
        }

        public String toString() {
            return String.format("%s", this.name);
        }
    }

    public static class RealSenseStream
    implements Comparable<RealSenseStream> {
        private int type;
        private int index;
        private Size size;
        private int frameRate;
        private int format;

        public RealSenseStream(int type, int index, Size size, int frameRate, int format) {
            this.type = type;
            this.index = index;
            this.size = size;
            this.frameRate = frameRate;
            this.format = format;
        }

        public int getType() {
            return this.type;
        }

        public int getIndex() {
            return this.index;
        }

        public Size getSize() {
            return this.size;
        }

        public int getFrameRate() {
            return this.frameRate;
        }

        public int getFormat() {
            return this.format;
        }

        @Override
        public int compareTo(RealSenseStream o) {
            return Integer.compare(this.getType(), o.getType());
        }
    }

    static class VideoStreamProfileData
    extends StreamProfileData {
        IntPointer width = new IntPointer(1L);
        IntPointer height = new IntPointer(1L);

        VideoStreamProfileData() {
        }
    }

    static class StreamProfileData {
        IntPointer nativeStreamIndex = new IntPointer(1L);
        IntPointer nativeFormatIndex = new IntPointer(1L);
        IntPointer index = new IntPointer(1L);
        IntPointer uniqueId = new IntPointer(1L);
        IntPointer frameRate = new IntPointer(1L);

        StreamProfileData() {
        }
    }
}

