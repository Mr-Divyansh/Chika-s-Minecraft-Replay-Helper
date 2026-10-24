import json

# Read the current mixins.json
with open('c:\\Users\\divy1\\OneDrive\\Desktop\\Divyansh all codes\\Chika Replay Helper\\yueyue-flashback.mixins.json', 'r') as f:
    data = json.load(f)

# Update compatibilityLevel from JAVA_26 to JAVA_25
data['compatibilityLevel'] = 'JAVA_25'

# Write back
with open('c:\\Users\\divy1\\OneDrive\\Desktop\\Divyansh all codes\\Chika Replay Helper\\yueyue-flashback.mixins.json', 'w') as f:
    json.dump(data, f, indent=2)

print("Updated yueyue-flashback.mixins.json: compatibilityLevel changed to JAVA_25")