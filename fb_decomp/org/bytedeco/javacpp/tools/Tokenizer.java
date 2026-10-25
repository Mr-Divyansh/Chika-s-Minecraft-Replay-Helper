/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import org.bytedeco.javacpp.tools.Token;

class Tokenizer
implements Closeable {
    File file = null;
    String text = null;
    Reader reader = null;
    String lineSeparator = null;
    int lastChar = -1;
    int lineNumber = 1;
    StringBuilder buffer = new StringBuilder();

    Tokenizer(Reader reader, File file, int lineNumber) {
        this.reader = reader;
        this.file = file;
        this.lineNumber = lineNumber;
    }

    Tokenizer(String text, File file, int lineNumber) {
        this.text = text;
        this.reader = new StringReader(text);
        this.file = file;
        this.lineNumber = lineNumber;
    }

    Tokenizer(File file) throws IOException {
        this(file, null);
    }

    Tokenizer(File file, String encoding) throws IOException {
        this.file = file;
        FileInputStream fis = new FileInputStream(file);
        this.reader = new BufferedReader(encoding != null ? new InputStreamReader((InputStream)fis, encoding) : new InputStreamReader(fis));
        this.reader.mark(1);
        if (this.reader.read() != 65279) {
            this.reader.reset();
        }
    }

    public void filterLines(String[] patterns, boolean skip) throws IOException {
        String line;
        BufferedReader lineReader;
        if (patterns == null) {
            return;
        }
        StringBuilder lines = new StringBuilder();
        BufferedReader bufferedReader = lineReader = this.reader instanceof BufferedReader ? (BufferedReader)this.reader : new BufferedReader(this.reader);
        block0: while ((line = lineReader.readLine()) != null) {
            int i;
            for (i = 0; i < patterns.length && !line.matches(patterns[i]); i += 2) {
            }
            if (i < patterns.length) {
                if (!skip) {
                    lines.append(line + "\n");
                }
                while (i + 1 < patterns.length && (line = lineReader.readLine()) != null) {
                    if (!skip) {
                        lines.append(line + "\n");
                    }
                    if (!line.matches(patterns[i + 1])) continue;
                    continue block0;
                }
                continue;
            }
            if (!skip) continue;
            lines.append(line + "\n");
        }
        this.reader.close();
        this.reader = new StringReader(lines.toString());
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    int readChar() throws IOException {
        if (this.lastChar != -1) {
            int c = this.lastChar;
            this.lastChar = -1;
            return c;
        }
        int c = this.reader.read();
        if (c == 13 || c == 10) {
            int c2;
            if (this.text == null) {
                ++this.lineNumber;
            }
            int n = c2 = c == 13 ? this.reader.read() : -1;
            if (this.lineSeparator == null) {
                String string = c == 13 && c2 == 10 ? "\r\n" : (this.lineSeparator = c == 13 ? "\r" : "\n");
            }
            if (c2 != 10) {
                this.lastChar = c2;
            }
            c = 10;
        }
        return c;
    }

    public Token nextToken() throws IOException {
        Token token = new Token();
        int c = this.readChar();
        this.buffer.setLength(0);
        if (Character.isWhitespace(c)) {
            this.buffer.append((char)c);
            while ((c = this.readChar()) != -1 && Character.isWhitespace(c)) {
                this.buffer.append((char)c);
            }
        }
        token.file = this.file;
        token.text = this.text;
        token.lineNumber = this.lineNumber;
        token.spacing = this.buffer.toString();
        this.buffer.setLength(0);
        if (Character.isLetter(c) || c == 95) {
            token.type = 5;
            this.buffer.append((char)c);
            while ((c = this.readChar()) != -1 && (Character.isDigit(c) || Character.isLetter(c) || c == 95)) {
                this.buffer.append((char)c);
            }
            token.value = this.buffer.toString();
            this.lastChar = c;
        } else if (Character.isDigit(c) || c == 46 || c == 45 || c == 43) {
            boolean small;
            boolean hex;
            boolean unsigned;
            boolean large;
            block78: {
                int c2;
                if (c == 46) {
                    c2 = this.readChar();
                    if (c2 == 46) {
                        int c3 = this.readChar();
                        if (c3 == 46) {
                            token.type = 6;
                            token.value = "...";
                            return token;
                        }
                        this.lastChar = c3;
                    } else {
                        this.lastChar = c2;
                    }
                } else if (c == 45) {
                    c2 = this.readChar();
                    if (c2 == 62) {
                        token.type = 6;
                        token.value = "->";
                        return token;
                    }
                    this.lastChar = c2;
                }
                token.type = c == 46 ? 2 : 1;
                this.buffer.append((char)c);
                int prevc = 0;
                boolean exp = false;
                large = false;
                unsigned = false;
                hex = false;
                small = false;
                while ((c = this.readChar()) != -1 && (Character.isDigit(c) || c == 46 || c == 45 || c == 43 || c >= 97 && c <= 102 || c == 105 || c == 108 || c == 117 || c == 120 || c >= 65 && c <= 70 || c == 73 || c == 76 || c == 85 || c == 88)) {
                    switch (c) {
                        case 46: {
                            token.type = 2;
                            break;
                        }
                        case 69: 
                        case 101: {
                            exp = true;
                            break;
                        }
                        case 76: 
                        case 108: {
                            large = true;
                            break;
                        }
                        case 85: 
                        case 117: {
                            unsigned = true;
                            break;
                        }
                        case 88: 
                        case 120: {
                            hex = true;
                        }
                    }
                    if (c != 108 && c != 76 && c != 117 && c != 85) {
                        this.buffer.append((char)c);
                    }
                    prevc = c;
                }
                if (!hex && (exp || prevc == 102 || prevc == 70)) {
                    token.type = 2;
                }
                if (token.type == 1 && !large) {
                    try {
                        long high = Long.decode(this.buffer.toString()) >> 16;
                        small = high == 0L || high == -1L;
                        large = (high >>= 16) != 0L && high != -1L;
                    }
                    catch (NumberFormatException e) {
                        if (this.buffer.length() < 16) break block78;
                        large = true;
                    }
                }
            }
            if (this.buffer.toString().endsWith("i64")) {
                this.buffer.setLength(this.buffer.length() - 3);
                large = true;
            }
            if (token.type == 1 && (large || unsigned && !hex && !small)) {
                this.buffer.append('L');
            }
            token.value = this.buffer.toString();
            this.lastChar = c;
        } else if (c == 39) {
            token.type = 1;
            c = this.readChar();
            boolean quote = true;
            if (c == 92) {
                c = this.readChar();
                if (c == 120) {
                    quote = false;
                    this.buffer.append('0');
                } else {
                    this.buffer.append('\'');
                    this.buffer.append('\\');
                }
            } else {
                this.buffer.append('\'');
            }
            this.buffer.append((char)c);
            while ((c = this.readChar()) != -1 && c != 39) {
                this.buffer.append((char)c);
                if (c != 92) continue;
                c = this.readChar();
                this.buffer.append((char)c);
            }
            if (quote) {
                this.buffer.append('\'');
            }
            token.value = this.buffer.toString();
        } else if (c == 34) {
            token.type = 3;
            this.buffer.append('\"');
            while ((c = this.readChar()) != -1 && c != 34) {
                this.buffer.append((char)c);
                if (c != 92) continue;
                c = this.readChar();
                this.buffer.append((char)c);
            }
            this.buffer.append('\"');
            token.value = this.buffer.toString();
        } else if (c == 47) {
            c = this.readChar();
            if (c == 47) {
                token.type = 4;
                this.buffer.append('/').append('/');
                int prevc = 0;
                while ((c = this.readChar()) != -1 && (prevc == 92 || c != 10)) {
                    this.buffer.append((char)c);
                    prevc = c;
                }
                token.value = this.buffer.toString();
                this.lastChar = c;
            } else if (c == 42) {
                token.type = 4;
                this.buffer.append('/').append('*');
                int prevc = 0;
                while ((c = this.readChar()) != -1 && (prevc != 42 || c != 47)) {
                    this.buffer.append((char)c);
                    prevc = c;
                }
                this.buffer.append('/');
                token.value = this.buffer.toString();
            } else {
                this.lastChar = c;
                token.type = 47;
            }
        } else if (c == 58) {
            int c2 = this.readChar();
            if (c2 == 58) {
                token.type = 6;
                token.value = "::";
            } else {
                token.type = c;
                this.lastChar = c2;
            }
        } else if (c == 38) {
            int c2 = this.readChar();
            if (c2 == 38) {
                token.type = 6;
                token.value = "&&";
            } else {
                token.type = c;
                this.lastChar = c2;
            }
        } else if (c == 35) {
            int c2 = this.readChar();
            if (c2 == 35) {
                token.type = 6;
                token.value = "##";
            } else {
                token.type = c;
                this.lastChar = c2;
            }
        } else if (c == 91) {
            int c2 = this.readChar();
            if (c2 == 91) {
                token.type = 6;
                token.value = "[[";
            } else {
                token.type = c;
                this.lastChar = c2;
            }
        } else if (c == 93) {
            int c2 = this.readChar();
            if (c2 == 93) {
                token.type = 6;
                token.value = "]]";
            } else {
                token.type = c;
                this.lastChar = c2;
            }
        } else if (c == 60) {
            int c2 = this.readChar();
            if (c2 == 61) {
                token.type = 6;
                token.value = "<=";
            } else {
                token.type = c;
                this.lastChar = c2;
            }
        } else if (c == 62) {
            int c2 = this.readChar();
            if (c2 == 61) {
                token.type = 6;
                token.value = ">=";
            } else {
                token.type = c;
                this.lastChar = c2;
            }
        } else {
            if (c == 92) {
                int c2 = this.readChar();
                if (c2 == 10) {
                    token.type = 4;
                    token.value = "\n";
                    return token;
                }
                this.lastChar = c2;
            }
            token.type = c;
        }
        return token;
    }

    Token[] tokenize() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        try {
            Token token;
            while (!(token = this.nextToken()).isEmpty()) {
                tokens.add(token);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return tokens.toArray(new Token[tokens.size()]);
    }
}

