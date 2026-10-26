package yueyue.flashback.capture;

public record UiImage(long serial, int width, int height, byte[] png, boolean screenOpen, boolean crosshair, int mouseX, int mouseY, int cursorScale, Object session) {

    public UiImage(long serial, int width, int height, byte[] png, boolean screenOpen, boolean crosshair, int mouseX, int mouseY, int cursorScale, Object session) {
        this.serial = serial;
        this.width = width;
        this.height = height;
        this.png = png;
        this.screenOpen = screenOpen;
        this.crosshair = crosshair;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.cursorScale = cursorScale;
        this.session = session;
    }

    public long serial() { return this.serial; }
    public int width() { return this.width; }
    public int height() { return this.height; }
    public byte[] png() { return this.png; }
    public boolean screenOpen() { return this.screenOpen; }
    public boolean crosshair() { return this.crosshair; }
    public int mouseX() { return this.mouseX; }
    public int mouseY() { return this.mouseY; }
    public int cursorScale() { return this.cursorScale; }
    public Object session() { return this.session; }
}
