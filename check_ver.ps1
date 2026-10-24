# Check Java class file major version bytes
$classFile = "c:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\verify_extract\yueyue\flashback\UiAction.class"
$bytes = Get-Content $classFile -Encoding Byte
# Byte 6 is the major version number (after magic CAFE BABE, minor version at byte 2-3, major at 4-5 actually byte 6 is part of major)
# Class file format: bytes 0-1 = magic (0xCAFEBABE), bytes 2-3 = minor version, bytes 4-7 = major version
$majorVersion = [int]$bytes[4] * 256 + [int]$bytes[5]
Write-Host "UiAction.class major version: $majorVersion"

# Check a few more classes
$classes = @(
    "yueyue/flashback/UiBootstrap",
    "yueyue/flashback/UiConfig",
    "yueyue/flashback/UiExportOptions",
    "yueyue/flashback/UiFrame",
    "yueyue/flashback/mixin/CameraMixin",
    "yueyue/flashback/mixin/RecorderMixin"
)

foreach ($cls in $classes) {
    $path = "c:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\verify_extract\$($cls.class).class"
    if (Test-Path $path) {
        $bytes = Get-Content $path -Encoding Byte
        $majorVersion = [int]$bytes[4] * 256 + [int]$bytes[5]
        Write-Host "$cls.class major version: $majorVersion"
    }
}