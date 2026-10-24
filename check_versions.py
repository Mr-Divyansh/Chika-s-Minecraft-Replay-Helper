import struct, os

extract_dir = r"c:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\verify_extract"

print("=== Class file Java versions in verify_extract ===")
for root, dirs, files in os.walk(extract_dir):
    for f in files:
        if f.endswith('.class'):
            p = os.path.join(root, f)
            with open(p, 'rb') as fh:
                fh.read(1)  # skip magic (0xCAFE)
                version = struct.unpack('B', fh.read(1))[0]
                rel = os.path.relpath(p, extract_dir)
                print(f"Java {version}: {rel}")