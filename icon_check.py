import struct, os
p = r'C:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\assets\yueyue_flashback\icon.png'
with open(p,'rb') as f:
    hdr = f.read(24)
print('magic', hdr[:8])
w,h = struct.unpack('>II', hdr[16:24])
print('dimensions', w, h)
print('size', os.path.getsize(p))
# check jar
import zipfile
jp = r'C:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\D-Web-Studio-Chika-Replay-Helper-1.1.0+mc26.1.2.jar'
z = zipfile.ZipFile(jp)
for n in z.namelist():
    if 'icon' in n.lower() or n.endswith('.png'):
        info = z.getinfo(n)
        data = z.read(n)
        # parse png dims
        if data[:8]==b'\x89PNG\r\n\x1a\n':
            w2,h2 = struct.unpack('>II', data[16:24])
            print('JAR entry', n, info.file_size, 'dims', w2, h2)
z.close()
