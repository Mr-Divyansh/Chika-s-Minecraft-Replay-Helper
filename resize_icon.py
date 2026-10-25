import os
import struct
import zlib

# Change to the project directory using short path
project_dir = r"C:\Users\divy1\OneDrive\Desktop\Divyansh all codes\CHIKAR~1"
os.chdir(project_dir)

def read_png_dimensions(filepath):
    """Read width and height from a PNG file."""
    with open(filepath, 'rb') as f:
        data = f.read()

    # Check PNG signature
    if data[:8] != b'\x89PNG\r\n\x1a\n':
        raise ValueError("Not a valid PNG file")

    # Parse IHDR chunk
    offset = 8
    while offset < len(data):
        chunk_length = struct.unpack('>I', data[offset:offset+4])[0]
        chunk_type = data[offset+4:offset+8]

        if chunk_type == b'IHDR':
            width = struct.unpack('>I', data[offset+8:offset+12])[0]
            height = struct.unpack('>I', data[offset+12:offset+16])[0]
            return width, height

        # Move to next chunk
        offset += 12 + chunk_length  # 4 (length) + 4 (type) + chunk_length + 4 (crc)

    raise ValueError("IHDR chunk not found")

def create_resized_icon(input_path, output_path, target_size=64):
    """Create a resized icon by modifying IHDR dimensions."""
    # Read the PNG data
    with open(input_path, 'rb') as f:
        data = bytearray(f.read())

    # Find and modify the IHDR chunk to set target size
    offset = 8
    while offset < len(data):
        chunk_length = struct.unpack('>I', data[offset:offset+4])[0]
        chunk_type = data[offset+4:offset+8]

        if chunk_type == b'IHDR':
            # Modify width and height
            data[offset+8:offset+12] = struct.pack('>I', target_size)
            data[offset+12:offset+16] = struct.pack('>I', target_size)
            print(f"Modified IHDR to {target_size}x{target_size}")
            break

        offset += 12 + chunk_length

    # Write the resized icon
    with open(output_path, 'wb') as f:
        f.write(data)

    print(f"Wrote resized icon to {output_path}")

# Use short DOS paths to avoid spaces in directory names
chika_path = r"C:\Users\divy1\OneDrive\Desktop\Divyansh all codes\CHIKAR~1\CHIKAS~1.PNG"
output_path = r"C:\Users\divy1\OneDrive\Desktop\Divyansh all codes\CHIKAR~1\assets\yueyue_flashback\icon.png"

# First, read source dimensions
orig_width, orig_height = read_png_dimensions(chika_path)
print(f"Source image size: {orig_width}x{orig_height}")

# Create resized icon
create_resized_icon(chika_path, output_path, 64)