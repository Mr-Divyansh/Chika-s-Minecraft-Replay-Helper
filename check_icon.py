import zipfile
jar_path = r'c:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\D-Web-Studio-Chika-Replay-Helper-1.1.0+mc26.1.2.jar'
z = zipfile.ZipFile(jar_path, 'r')
print('Icon-related entries in JAR:')
for name in z.namelist():
    lower = name.lower()
    if 'icon' in lower or 'png' in lower or 'yueyue' in lower:
        info = z.getinfo(name)
        print(f'  {name} ({info.file_size} bytes)')
z.close()