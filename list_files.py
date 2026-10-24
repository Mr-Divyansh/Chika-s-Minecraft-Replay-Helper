import os
base = r'c:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper'
for root, dirs, files in os.walk(base):
    for f in files:
        path = os.path.join(root, f)
        print(path)