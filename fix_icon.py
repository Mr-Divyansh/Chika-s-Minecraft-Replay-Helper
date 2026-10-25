import zipfile
import os

jar_path = r"C:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\D-Web-Studio-Chika-Replay-Helper-1.1.0+mc26.1.2.jar"
project_icon = r"C:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\assets\yueyue_flashback\icon.png"

# Read the modified icon
with open(project_icon, "rb") as f:
    new_icon_data = f.read()
print(f"Modified icon: {len(new_icon_data)} bytes")

# Read the JAR and replace the icon entry
working_dir = r"C:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\jar_work"
if os.path.exists(working_dir):
    os.rmdir(working_dir)

# Extract JAR to work directory
os.makedirs(working_dir, exist_ok=True)
with zipfile.ZipFile(jar_path, "r") as z:
    z.extractall(working_dir)

# Find and replace the icon.png
icon_path_in_jar = os.path.join(working_dir, "assets", "yueyue_flashback", "icon.png")
with open(icon_path_in_jar, "wb") as f:
    f.write(new_icon_data)
print(f"Replaced icon at: {icon_path_in_jar}")

# Get the icon dimensions
import struct
with open(icon_path_in_jar, "rb") as f:
    d = f.read()
width = struct.unpack(">I", d[16:20])[0]
height = struct.unpack(">I", d[20:24])[0]
print(f"Icon dimensions: {width}x{height}")

# Create new JAR from work directory
new_jar_path = r"C:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\D-Web-Studio-Chika-Replay-Helper-1.1.0+mc26.1.2.jar.new"
with zipfile.ZipFile(new_jar_path, "w", zipfile.ZIP_DEFLATED) as new_zf:
    for root, dirs, files in os.walk(working_dir):
        for file in files:
            file_path = os.path.join(root, file)
            arcname = os.path.relpath(file_path, working_dir)
            new_zf.write(file_path, arcname)
print(f"Created new JAR: {new_jar_path}")

# Verify the new JAR
with zipfile.ZipFile(new_jar_path, "r") as z:
    for n in z.namelist():
        if "icon" in n.lower():
            data = z.read(n)
            w = struct.unpack(">I", data[16:20])[0]
            h = struct.unpack(">I", d[20:24] if False else [d[20:24]])[0] if False else 0
            print(f"Verified icon in JAR: {n} ({w}x{h})")

# Clean up
import shutil
shutil.rmtree(working_dir)
print("Done!")