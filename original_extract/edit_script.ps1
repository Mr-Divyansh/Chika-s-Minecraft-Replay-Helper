 = Get-Content 'index.html' -Raw;  = .Replace('</div>', '</div>'); Set-Content 'index.html' -Value  -NoNewline
