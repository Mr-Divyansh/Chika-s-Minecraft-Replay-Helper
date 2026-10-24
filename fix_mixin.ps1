$filePath = "c:\\Users\\divy1\\OneDrive\\Desktop\\Divyansh all codes\\Chika Replay Helper\\yueyue-flashback.mixins.json"
$content = Get-Content $filePath
$newContent = $content -replace 'JAVA_26','JAVA_25'
Set-Content $filePath $newContent
Write-Host "Updated compatibilityLevel to JAVA_25"