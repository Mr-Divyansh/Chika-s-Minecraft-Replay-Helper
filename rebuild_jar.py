import zipfile
import shutil
import os

jar_path = r'c:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\D-Web-Studio-Chika-Replay-Helper-1.1.0+mc26.1.2.jar'
work_dir = r'c:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\jar_rebuild_work'

# Clean up any previous work directory
if os.path.exists(work_dir):
    shutil.rmtree(work_dir)
os.makedirs(work_dir, exist_ok=True)

# Extract existing JAR contents
print("Extracting existing JAR...")
with zipfile.ZipFile(jar_path, 'r') as z:
    z.extractall(work_dir)

# Update fabric.mod.json
mod_json_path = os.path.join(work_dir, 'fabric.mod.json')
print(f"Updating {mod_json_path}")

import json
with open(mod_json_path, 'r', encoding='utf-8') as f:
    mod_data = json.load(f)

# Update the authors field
mod_data['authors'] = ['D Web Studio']

with open(mod_json_path, 'w', encoding='utf-8') as f:
    json.dump(mod_data, f, indent=2)

print(f"Updated authors to: {mod_data['authors']}")
print(f"Name: {mod_data['name']}")
print(f"Icon: {mod_data['icon']}")

# Remove old JAR and create new one
print("\nCreating new JAR...")
os.remove(jar_path)

new_jar_path = jar_path
with zipfile.ZipFile(new_jar_path, 'w', zipfile.ZIP_DEFLATED) as z:
    for root, dirs, files in os.walk(work_dir):
        for file in files:
            file_path = os.path.join(root, file)
            arcname = os.path.relpath(file_path, work_dir)
            # Avoid duplicate entries - check if entry already exists
            z.write(file_path, arcname)

print(f"New JAR created at: {new_jar_path}")

# Clean up
shutil.rmtree(work_dir)

# Verify
print("\nVerification:")
z = zipfile.ZipFile(new_jar_path, 'r')
print("JAR contents:")
for name in z.namelist():
    print(f"  {name}")
z.close()