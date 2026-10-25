/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  org.lwjgl.BufferUtils
 */
package com.moulberry.flashback.sound;

import com.moulberry.flashback.Flashback;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import net.minecraft.util.Mth;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVIOContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avformat.Read_packet_Pointer_BytePointer_int;
import org.bytedeco.ffmpeg.avutil.AVChannelLayout;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.ffmpeg.global.swresample;
import org.bytedeco.ffmpeg.swresample.SwrContext;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.lwjgl.BufferUtils;

public class FFmpegAudioReader {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static RawAudioData read(final InputStream inputStream) {
        Object object;
        BytePointer convertOutPtr;
        PointerPointer planeInPtr;
        PointerPointer planeOutPtr;
        SwrContext swrContext;
        AVPacket avPacket;
        AVFrame avFrame;
        AVCodecContext codecContext;
        AVIOContext avioContext;
        AVFormatContext formatContext;
        block174: {
            block175: {
                RawAudioData endOfStream2;
                block172: {
                    block173: {
                        RawAudioData rawAudioData;
                        block170: {
                            block171: {
                                block168: {
                                    block169: {
                                        block166: {
                                            block167: {
                                                RawAudioData rawAudioData2;
                                                block164: {
                                                    block165: {
                                                        RawAudioData rawAudioData3;
                                                        block162: {
                                                            block163: {
                                                                RawAudioData streamIndex22222;
                                                                block160: {
                                                                    block161: {
                                                                        RawAudioData streamIndex22222;
                                                                        block158: {
                                                                            block159: {
                                                                                RawAudioData streamIndex22222;
                                                                                block156: {
                                                                                    block157: {
                                                                                        RawAudioData streamIndex22222;
                                                                                        block154: {
                                                                                            block155: {
                                                                                                RawAudioData streamIndex22222;
                                                                                                block152: {
                                                                                                    block153: {
                                                                                                        Read_packet_Pointer_BytePointer_int readCallback = new Read_packet_Pointer_BytePointer_int(){

                                                                                                            @Override
                                                                                                            public int call(Pointer pointer, BytePointer bytePointer, int size) {
                                                                                                                try {
                                                                                                                    byte[] b = new byte[size];
                                                                                                                    int read = inputStream.read(b, 0, size);
                                                                                                                    if (read < 0) {
                                                                                                                        return avutil.AVERROR_EOF();
                                                                                                                    }
                                                                                                                    bytePointer.put(b, 0, read);
                                                                                                                    return read;
                                                                                                                }
                                                                                                                catch (Throwable t) {
                                                                                                                    Flashback.LOGGER.error("Error while reading audio data", t);
                                                                                                                    return -1;
                                                                                                                }
                                                                                                            }
                                                                                                        };
                                                                                                        formatContext = null;
                                                                                                        avioContext = null;
                                                                                                        codecContext = null;
                                                                                                        avFrame = null;
                                                                                                        avPacket = null;
                                                                                                        swrContext = null;
                                                                                                        planeOutPtr = (PointerPointer)new PointerPointer(8L).retainReference();
                                                                                                        planeInPtr = (PointerPointer)new PointerPointer(8L).retainReference();
                                                                                                        convertOutPtr = null;
                                                                                                        try {
                                                                                                            formatContext = avformat.avformat_alloc_context();
                                                                                                            avioContext = avformat.avio_alloc_context(new BytePointer(avutil.av_malloc(4096L)), 4096, 0, (Pointer)formatContext, readCallback, null, null);
                                                                                                            formatContext.pb(avioContext);
                                                                                                            if (avformat.avformat_open_input(formatContext, inputStream.toString(), null, null) < 0) {
                                                                                                                Flashback.LOGGER.error("avformat_open_input error");
                                                                                                                RawAudioData rawAudioData4 = null;
                                                                                                                return rawAudioData4;
                                                                                                            }
                                                                                                            if (avformat.avformat_find_stream_info(formatContext, (AVDictionary)null) < 0) {
                                                                                                                Flashback.LOGGER.error("avformat_find_stream_info error");
                                                                                                                RawAudioData rawAudioData5 = null;
                                                                                                                return rawAudioData5;
                                                                                                            }
                                                                                                            AVStream audioStream = null;
                                                                                                            AVCodecParameters audioCodecParameters = null;
                                                                                                            AVCodec audioCodec = null;
                                                                                                            int numStreams = formatContext.nb_streams();
                                                                                                            for (int streamIndex22222 = 0; streamIndex22222 < numStreams; ++streamIndex22222) {
                                                                                                                AVCodec decoder;
                                                                                                                AVStream stream = formatContext.streams(streamIndex22222);
                                                                                                                AVCodecParameters codecParameters = stream.codecpar();
                                                                                                                if (codecParameters.codec_type() != 1 || (decoder = avcodec.avcodec_find_decoder(codecParameters.codec_id())) == null) continue;
                                                                                                                audioStream = stream;
                                                                                                                audioCodecParameters = codecParameters;
                                                                                                                audioCodec = decoder;
                                                                                                            }
                                                                                                            if (audioStream == null) {
                                                                                                                Flashback.LOGGER.error("Unable to find audio stream");
                                                                                                                streamIndex22222 = null;
                                                                                                                if (planeOutPtr == null) break block152;
                                                                                                                break block153;
                                                                                                            }
                                                                                                            codecContext = avcodec.avcodec_alloc_context3(audioCodec);
                                                                                                            if (avcodec.avcodec_parameters_to_context(codecContext, audioCodecParameters) < 0) {
                                                                                                                Flashback.LOGGER.error("Unable to copy parameters from stream to codec");
                                                                                                                streamIndex22222 = null;
                                                                                                                if (planeOutPtr == null) break block154;
                                                                                                                break block155;
                                                                                                            }
                                                                                                            if (avcodec.avcodec_open2(codecContext, audioCodec, (AVDictionary)null) < 0) {
                                                                                                                Flashback.LOGGER.error("Error opening codec");
                                                                                                                streamIndex22222 = null;
                                                                                                                if (planeOutPtr == null) break block156;
                                                                                                                break block157;
                                                                                                            }
                                                                                                            avFrame = avutil.av_frame_alloc();
                                                                                                            if (avFrame == null) {
                                                                                                                Flashback.LOGGER.error("Unable to allocate av_frame");
                                                                                                                streamIndex22222 = null;
                                                                                                                if (planeOutPtr == null) break block158;
                                                                                                                break block159;
                                                                                                            }
                                                                                                            avPacket = avcodec.av_packet_alloc();
                                                                                                            if (avPacket == null) {
                                                                                                                Flashback.LOGGER.error("Unable to allocate av_packet");
                                                                                                                streamIndex22222 = null;
                                                                                                                if (planeOutPtr == null) break block160;
                                                                                                                break block161;
                                                                                                            }
                                                                                                            int audioStreamIndex = audioStream.index();
                                                                                                            AVChannelLayout originalChannelLayout = codecContext.ch_layout();
                                                                                                            int originalSampleFormat = codecContext.sample_fmt();
                                                                                                            AVChannelLayout channelLayout = originalChannelLayout.nb_channels() == 2 ? avutil.AV_CHANNEL_LAYOUT_STEREO() : avutil.AV_CHANNEL_LAYOUT_MONO();
                                                                                                            int sampleFormat = originalSampleFormat;
                                                                                                            if (sampleFormat != 0 && sampleFormat != 1) {
                                                                                                                sampleFormat = 1;
                                                                                                            }
                                                                                                            int sampleRate = codecContext.sample_rate();
                                                                                                            if (avutil.av_channel_layout_compare(originalChannelLayout, channelLayout) != 0 || sampleFormat != originalSampleFormat) {
                                                                                                                swrContext = new SwrContext();
                                                                                                                int res = swresample.swr_alloc_set_opts2(swrContext, channelLayout, sampleFormat, sampleRate, originalChannelLayout, originalSampleFormat, sampleRate, 0, null);
                                                                                                                if (res != 0 || swrContext.isNull()) {
                                                                                                                    Flashback.LOGGER.error("Unable to allocate swr convert context");
                                                                                                                    rawAudioData3 = null;
                                                                                                                    if (planeOutPtr == null) break block162;
                                                                                                                    break block163;
                                                                                                                }
                                                                                                                if (swresample.swr_init(swrContext) < 0) {
                                                                                                                    Flashback.LOGGER.error("Unable to initialize swr convert context");
                                                                                                                    rawAudioData2 = null;
                                                                                                                    if (planeOutPtr == null) break block164;
                                                                                                                    break block165;
                                                                                                                }
                                                                                                            }
                                                                                                            int lastBufferCapacity = 4096;
                                                                                                            ArrayList<ByteBuffer> rawDataBuffers = new ArrayList<ByteBuffer>();
                                                                                                            ByteBuffer currentByteBuffer = null;
                                                                                                            while (true) {
                                                                                                                boolean endOfStream2 = false;
                                                                                                                int ret = avformat.av_read_frame(formatContext, avPacket);
                                                                                                                if (ret == org.bytedeco.ffmpeg.presets.avutil.AVERROR_EAGAIN()) continue;
                                                                                                                if (ret < 0) {
                                                                                                                    avPacket.stream_index(audioStreamIndex);
                                                                                                                    avPacket.flags(1);
                                                                                                                    avPacket.data(null);
                                                                                                                    avPacket.size(0);
                                                                                                                    endOfStream2 = true;
                                                                                                                }
                                                                                                                if (avPacket.stream_index() != audioStreamIndex) continue;
                                                                                                                ret = avcodec.avcodec_send_packet(codecContext, avPacket);
                                                                                                                if (ret < 0) {
                                                                                                                    Flashback.LOGGER.error("Error sending packet to decoder: {}", (Object)ret);
                                                                                                                    object = null;
                                                                                                                    if (planeOutPtr == null) break block166;
                                                                                                                    break block167;
                                                                                                                }
                                                                                                                while ((ret = avcodec.avcodec_receive_frame(codecContext, avFrame)) != org.bytedeco.ffmpeg.presets.avutil.AVERROR_EAGAIN() && ret != avutil.AVERROR_EOF()) {
                                                                                                                    ByteBuffer outBuffer;
                                                                                                                    if (ret < 0) {
                                                                                                                        Flashback.LOGGER.error("Error receiving frame from decoder: {}", (Object)ret);
                                                                                                                        object = null;
                                                                                                                        if (planeOutPtr == null) break block168;
                                                                                                                        break block169;
                                                                                                                    }
                                                                                                                    if (swrContext != null) {
                                                                                                                        int bufferInPlanes = avutil.av_sample_fmt_is_planar(originalSampleFormat) != 0 ? avFrame.ch_layout().nb_channels() : 1;
                                                                                                                        int samplesIn = avFrame.nb_samples();
                                                                                                                        int bufferInSize = avutil.av_samples_get_buffer_size((IntPointer)null, originalChannelLayout.nb_channels(), samplesIn, originalSampleFormat, 1) / bufferInPlanes;
                                                                                                                        Pointer[] pointersIn = new BytePointer[bufferInPlanes];
                                                                                                                        for (int plane = 0; plane < bufferInPlanes; ++plane) {
                                                                                                                            pointersIn[plane] = avFrame.data(plane).capacity(bufferInSize);
                                                                                                                        }
                                                                                                                        int samplesOut = swresample.swr_get_out_samples(swrContext, samplesIn);
                                                                                                                        int sampleOutBytes = avutil.av_get_bytes_per_sample(sampleFormat);
                                                                                                                        int bufferOutSize = samplesOut * sampleOutBytes * channelLayout.nb_channels();
                                                                                                                        Pointer[] pointersOut = new BytePointer[1];
                                                                                                                        if (convertOutPtr == null || convertOutPtr.capacity() < (long)bufferOutSize) {
                                                                                                                            if (convertOutPtr != null) {
                                                                                                                                avutil.av_free(convertOutPtr.position(0L));
                                                                                                                            }
                                                                                                                            convertOutPtr = new BytePointer(avutil.av_malloc(bufferOutSize)).capacity(bufferOutSize);
                                                                                                                        }
                                                                                                                        pointersOut[0] = convertOutPtr;
                                                                                                                        ret = swresample.swr_convert(swrContext, planeOutPtr.put(pointersOut), samplesOut, planeInPtr.put(pointersIn), samplesIn);
                                                                                                                        if (ret < 0) {
                                                                                                                            Flashback.LOGGER.error("Error running swr_convert: {}", (Object)ret);
                                                                                                                            rawAudioData = null;
                                                                                                                            if (planeOutPtr == null) break block170;
                                                                                                                            break block171;
                                                                                                                        }
                                                                                                                        int outSize = ret * sampleOutBytes * channelLayout.nb_channels();
                                                                                                                        ((BytePointer)pointersOut[0]).position(0L).limit(outSize);
                                                                                                                        outBuffer = ((BytePointer)pointersOut[0]).asBuffer().position(0).limit(outSize);
                                                                                                                    } else {
                                                                                                                        int bufferSize = avutil.av_samples_get_buffer_size((IntPointer)null, channelLayout.nb_channels(), avFrame.nb_samples(), sampleFormat, 1);
                                                                                                                        BytePointer outPointer = avFrame.data(0).position(0L).limit(bufferSize);
                                                                                                                        outBuffer = outPointer.asBuffer().position(0).limit(bufferSize);
                                                                                                                    }
                                                                                                                    if (currentByteBuffer == null || outBuffer.remaining() > currentByteBuffer.remaining()) {
                                                                                                                        if (currentByteBuffer != null) {
                                                                                                                            currentByteBuffer.flip();
                                                                                                                            rawDataBuffers.add(currentByteBuffer);
                                                                                                                        }
                                                                                                                        lastBufferCapacity = Math.max(lastBufferCapacity * 2, Mth.smallestEncompassingPowerOfTwo((int)outBuffer.remaining()));
                                                                                                                        currentByteBuffer = BufferUtils.createByteBuffer((int)lastBufferCapacity);
                                                                                                                    }
                                                                                                                    currentByteBuffer.put(outBuffer);
                                                                                                                }
                                                                                                                avcodec.av_packet_unref(avPacket);
                                                                                                                if (endOfStream2) break;
                                                                                                            }
                                                                                                            if (currentByteBuffer == null) {
                                                                                                                Flashback.LOGGER.error("Unable to read audio file: no data");
                                                                                                                endOfStream2 = null;
                                                                                                                if (planeOutPtr == null) break block172;
                                                                                                                break block173;
                                                                                                            }
                                                                                                            currentByteBuffer.flip();
                                                                                                            rawDataBuffers.add(currentByteBuffer);
                                                                                                            int finalSize = 0;
                                                                                                            for (ByteBuffer rawDataBuffer : rawDataBuffers) {
                                                                                                                finalSize += rawDataBuffer.remaining();
                                                                                                            }
                                                                                                            ByteBuffer finalBuffer = BufferUtils.createByteBuffer((int)finalSize);
                                                                                                            for (ByteBuffer rawDataBuffer : rawDataBuffers) {
                                                                                                                finalBuffer.put(rawDataBuffer);
                                                                                                            }
                                                                                                            finalBuffer.flip();
                                                                                                            object = new RawAudioData(finalBuffer, new AudioFormat(sampleRate, sampleFormat == 0 ? 8 : 16, channelLayout.nb_channels(), true, ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN));
                                                                                                            if (planeOutPtr == null) break block174;
                                                                                                            break block175;
                                                                                                        }
                                                                                                        catch (Exception e) {
                                                                                                            Flashback.LOGGER.error("Exception thrown while reading audio", (Throwable)e);
                                                                                                            RawAudioData rawAudioData6 = null;
                                                                                                            return rawAudioData6;
                                                                                                        }
                                                                                                    }
                                                                                                    planeOutPtr.releaseReference();
                                                                                                }
                                                                                                if (planeInPtr != null) {
                                                                                                    planeInPtr.releaseReference();
                                                                                                }
                                                                                                if (convertOutPtr != null) {
                                                                                                    avutil.av_free(convertOutPtr.position(0L));
                                                                                                }
                                                                                                if (formatContext != null) {
                                                                                                    avformat.avformat_free_context(formatContext);
                                                                                                }
                                                                                                if (avioContext != null) {
                                                                                                    if (avioContext.buffer() != null) {
                                                                                                        avutil.av_free(avioContext.buffer());
                                                                                                        avioContext.buffer(null);
                                                                                                    }
                                                                                                    avutil.av_free(avioContext);
                                                                                                }
                                                                                                if (codecContext != null) {
                                                                                                    avcodec.avcodec_free_context(codecContext);
                                                                                                }
                                                                                                if (avFrame != null) {
                                                                                                    avutil.av_frame_free(avFrame);
                                                                                                }
                                                                                                if (avPacket != null) {
                                                                                                    avcodec.av_packet_free(avPacket);
                                                                                                }
                                                                                                if (swrContext == null) return streamIndex22222;
                                                                                                swresample.swr_free(swrContext);
                                                                                                return streamIndex22222;
                                                                                            }
                                                                                            planeOutPtr.releaseReference();
                                                                                        }
                                                                                        if (planeInPtr != null) {
                                                                                            planeInPtr.releaseReference();
                                                                                        }
                                                                                        if (convertOutPtr != null) {
                                                                                            avutil.av_free(convertOutPtr.position(0L));
                                                                                        }
                                                                                        if (formatContext != null) {
                                                                                            avformat.avformat_free_context(formatContext);
                                                                                        }
                                                                                        if (avioContext != null) {
                                                                                            if (avioContext.buffer() != null) {
                                                                                                avutil.av_free(avioContext.buffer());
                                                                                                avioContext.buffer(null);
                                                                                            }
                                                                                            avutil.av_free(avioContext);
                                                                                        }
                                                                                        if (codecContext != null) {
                                                                                            avcodec.avcodec_free_context(codecContext);
                                                                                        }
                                                                                        if (avFrame != null) {
                                                                                            avutil.av_frame_free(avFrame);
                                                                                        }
                                                                                        if (avPacket != null) {
                                                                                            avcodec.av_packet_free(avPacket);
                                                                                        }
                                                                                        if (swrContext == null) return streamIndex22222;
                                                                                        swresample.swr_free(swrContext);
                                                                                        return streamIndex22222;
                                                                                    }
                                                                                    planeOutPtr.releaseReference();
                                                                                }
                                                                                if (planeInPtr != null) {
                                                                                    planeInPtr.releaseReference();
                                                                                }
                                                                                if (convertOutPtr != null) {
                                                                                    avutil.av_free(convertOutPtr.position(0L));
                                                                                }
                                                                                if (formatContext != null) {
                                                                                    avformat.avformat_free_context(formatContext);
                                                                                }
                                                                                if (avioContext != null) {
                                                                                    if (avioContext.buffer() != null) {
                                                                                        avutil.av_free(avioContext.buffer());
                                                                                        avioContext.buffer(null);
                                                                                    }
                                                                                    avutil.av_free(avioContext);
                                                                                }
                                                                                if (codecContext != null) {
                                                                                    avcodec.avcodec_free_context(codecContext);
                                                                                }
                                                                                if (avFrame != null) {
                                                                                    avutil.av_frame_free(avFrame);
                                                                                }
                                                                                if (avPacket != null) {
                                                                                    avcodec.av_packet_free(avPacket);
                                                                                }
                                                                                if (swrContext == null) return streamIndex22222;
                                                                                swresample.swr_free(swrContext);
                                                                                return streamIndex22222;
                                                                            }
                                                                            planeOutPtr.releaseReference();
                                                                        }
                                                                        if (planeInPtr != null) {
                                                                            planeInPtr.releaseReference();
                                                                        }
                                                                        if (convertOutPtr != null) {
                                                                            avutil.av_free(convertOutPtr.position(0L));
                                                                        }
                                                                        if (formatContext != null) {
                                                                            avformat.avformat_free_context(formatContext);
                                                                        }
                                                                        if (avioContext != null) {
                                                                            if (avioContext.buffer() != null) {
                                                                                avutil.av_free(avioContext.buffer());
                                                                                avioContext.buffer(null);
                                                                            }
                                                                            avutil.av_free(avioContext);
                                                                        }
                                                                        if (codecContext != null) {
                                                                            avcodec.avcodec_free_context(codecContext);
                                                                        }
                                                                        if (avFrame != null) {
                                                                            avutil.av_frame_free(avFrame);
                                                                        }
                                                                        if (avPacket != null) {
                                                                            avcodec.av_packet_free(avPacket);
                                                                        }
                                                                        if (swrContext == null) return streamIndex22222;
                                                                        swresample.swr_free(swrContext);
                                                                        return streamIndex22222;
                                                                    }
                                                                    planeOutPtr.releaseReference();
                                                                }
                                                                if (planeInPtr != null) {
                                                                    planeInPtr.releaseReference();
                                                                }
                                                                if (convertOutPtr != null) {
                                                                    avutil.av_free(convertOutPtr.position(0L));
                                                                }
                                                                if (formatContext != null) {
                                                                    avformat.avformat_free_context(formatContext);
                                                                }
                                                                if (avioContext != null) {
                                                                    if (avioContext.buffer() != null) {
                                                                        avutil.av_free(avioContext.buffer());
                                                                        avioContext.buffer(null);
                                                                    }
                                                                    avutil.av_free(avioContext);
                                                                }
                                                                if (codecContext != null) {
                                                                    avcodec.avcodec_free_context(codecContext);
                                                                }
                                                                if (avFrame != null) {
                                                                    avutil.av_frame_free(avFrame);
                                                                }
                                                                if (avPacket != null) {
                                                                    avcodec.av_packet_free(avPacket);
                                                                }
                                                                if (swrContext == null) return streamIndex22222;
                                                                swresample.swr_free(swrContext);
                                                                return streamIndex22222;
                                                            }
                                                            planeOutPtr.releaseReference();
                                                        }
                                                        if (planeInPtr != null) {
                                                            planeInPtr.releaseReference();
                                                        }
                                                        if (convertOutPtr != null) {
                                                            avutil.av_free(convertOutPtr.position(0L));
                                                        }
                                                        if (formatContext != null) {
                                                            avformat.avformat_free_context(formatContext);
                                                        }
                                                        if (avioContext != null) {
                                                            if (avioContext.buffer() != null) {
                                                                avutil.av_free(avioContext.buffer());
                                                                avioContext.buffer(null);
                                                            }
                                                            avutil.av_free(avioContext);
                                                        }
                                                        if (codecContext != null) {
                                                            avcodec.avcodec_free_context(codecContext);
                                                        }
                                                        if (avFrame != null) {
                                                            avutil.av_frame_free(avFrame);
                                                        }
                                                        if (avPacket != null) {
                                                            avcodec.av_packet_free(avPacket);
                                                        }
                                                        if (swrContext == null) return rawAudioData3;
                                                        swresample.swr_free(swrContext);
                                                        return rawAudioData3;
                                                    }
                                                    planeOutPtr.releaseReference();
                                                }
                                                if (planeInPtr != null) {
                                                    planeInPtr.releaseReference();
                                                }
                                                if (convertOutPtr != null) {
                                                    avutil.av_free(convertOutPtr.position(0L));
                                                }
                                                if (formatContext != null) {
                                                    avformat.avformat_free_context(formatContext);
                                                }
                                                if (avioContext != null) {
                                                    if (avioContext.buffer() != null) {
                                                        avutil.av_free(avioContext.buffer());
                                                        avioContext.buffer(null);
                                                    }
                                                    avutil.av_free(avioContext);
                                                }
                                                if (codecContext != null) {
                                                    avcodec.avcodec_free_context(codecContext);
                                                }
                                                if (avFrame != null) {
                                                    avutil.av_frame_free(avFrame);
                                                }
                                                if (avPacket != null) {
                                                    avcodec.av_packet_free(avPacket);
                                                }
                                                if (swrContext == null) return rawAudioData2;
                                                swresample.swr_free(swrContext);
                                                return rawAudioData2;
                                            }
                                            planeOutPtr.releaseReference();
                                        }
                                        if (planeInPtr != null) {
                                            planeInPtr.releaseReference();
                                        }
                                        if (convertOutPtr != null) {
                                            avutil.av_free(convertOutPtr.position(0L));
                                        }
                                        if (formatContext != null) {
                                            avformat.avformat_free_context(formatContext);
                                        }
                                        if (avioContext != null) {
                                            if (avioContext.buffer() != null) {
                                                avutil.av_free(avioContext.buffer());
                                                avioContext.buffer(null);
                                            }
                                            avutil.av_free(avioContext);
                                        }
                                        if (codecContext != null) {
                                            avcodec.avcodec_free_context(codecContext);
                                        }
                                        if (avFrame != null) {
                                            avutil.av_frame_free(avFrame);
                                        }
                                        if (avPacket != null) {
                                            avcodec.av_packet_free(avPacket);
                                        }
                                        if (swrContext == null) return object;
                                        swresample.swr_free(swrContext);
                                        return object;
                                    }
                                    planeOutPtr.releaseReference();
                                }
                                if (planeInPtr != null) {
                                    planeInPtr.releaseReference();
                                }
                                if (convertOutPtr != null) {
                                    avutil.av_free(convertOutPtr.position(0L));
                                }
                                if (formatContext != null) {
                                    avformat.avformat_free_context(formatContext);
                                }
                                if (avioContext != null) {
                                    if (avioContext.buffer() != null) {
                                        avutil.av_free(avioContext.buffer());
                                        avioContext.buffer(null);
                                    }
                                    avutil.av_free(avioContext);
                                }
                                if (codecContext != null) {
                                    avcodec.avcodec_free_context(codecContext);
                                }
                                if (avFrame != null) {
                                    avutil.av_frame_free(avFrame);
                                }
                                if (avPacket != null) {
                                    avcodec.av_packet_free(avPacket);
                                }
                                if (swrContext == null) return object;
                                swresample.swr_free(swrContext);
                                return object;
                            }
                            planeOutPtr.releaseReference();
                        }
                        if (planeInPtr != null) {
                            planeInPtr.releaseReference();
                        }
                        if (convertOutPtr != null) {
                            avutil.av_free(convertOutPtr.position(0L));
                        }
                        if (formatContext != null) {
                            avformat.avformat_free_context(formatContext);
                        }
                        if (avioContext != null) {
                            if (avioContext.buffer() != null) {
                                avutil.av_free(avioContext.buffer());
                                avioContext.buffer(null);
                            }
                            avutil.av_free(avioContext);
                        }
                        if (codecContext != null) {
                            avcodec.avcodec_free_context(codecContext);
                        }
                        if (avFrame != null) {
                            avutil.av_frame_free(avFrame);
                        }
                        if (avPacket != null) {
                            avcodec.av_packet_free(avPacket);
                        }
                        if (swrContext == null) return rawAudioData;
                        swresample.swr_free(swrContext);
                        return rawAudioData;
                    }
                    planeOutPtr.releaseReference();
                }
                if (planeInPtr != null) {
                    planeInPtr.releaseReference();
                }
                if (convertOutPtr != null) {
                    avutil.av_free(convertOutPtr.position(0L));
                }
                if (formatContext != null) {
                    avformat.avformat_free_context(formatContext);
                }
                if (avioContext != null) {
                    if (avioContext.buffer() != null) {
                        avutil.av_free(avioContext.buffer());
                        avioContext.buffer(null);
                    }
                    avutil.av_free(avioContext);
                }
                if (codecContext != null) {
                    avcodec.avcodec_free_context(codecContext);
                }
                if (avFrame != null) {
                    avutil.av_frame_free(avFrame);
                }
                if (avPacket != null) {
                    avcodec.av_packet_free(avPacket);
                }
                if (swrContext == null) return endOfStream2;
                swresample.swr_free(swrContext);
                return endOfStream2;
            }
            planeOutPtr.releaseReference();
        }
        if (planeInPtr != null) {
            planeInPtr.releaseReference();
        }
        if (convertOutPtr != null) {
            avutil.av_free(convertOutPtr.position(0L));
        }
        if (formatContext != null) {
            avformat.avformat_free_context(formatContext);
        }
        if (avioContext != null) {
            if (avioContext.buffer() != null) {
                avutil.av_free(avioContext.buffer());
                avioContext.buffer(null);
            }
            avutil.av_free(avioContext);
        }
        if (codecContext != null) {
            avcodec.avcodec_free_context(codecContext);
        }
        if (avFrame != null) {
            avutil.av_frame_free(avFrame);
        }
        if (avPacket != null) {
            avcodec.av_packet_free(avPacket);
        }
        if (swrContext == null) return object;
        swresample.swr_free(swrContext);
        return object;
        finally {
            if (planeOutPtr != null) {
                planeOutPtr.releaseReference();
            }
            if (planeInPtr != null) {
                planeInPtr.releaseReference();
            }
            if (convertOutPtr != null) {
                avutil.av_free(convertOutPtr.position(0L));
            }
            if (formatContext != null) {
                avformat.avformat_free_context(formatContext);
            }
            if (avioContext != null) {
                if (avioContext.buffer() != null) {
                    avutil.av_free(avioContext.buffer());
                    avioContext.buffer(null);
                }
                avutil.av_free(avioContext);
            }
            if (codecContext != null) {
                avcodec.avcodec_free_context(codecContext);
            }
            if (avFrame != null) {
                avutil.av_frame_free(avFrame);
            }
            if (avPacket != null) {
                avcodec.av_packet_free(avPacket);
            }
            if (swrContext != null) {
                swresample.swr_free(swrContext);
            }
        }
    }

    public record RawAudioData(ByteBuffer byteBuffer, AudioFormat audioFormat) {
    }
}

