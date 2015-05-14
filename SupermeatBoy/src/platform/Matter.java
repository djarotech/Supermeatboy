package platform;

import java.awt.Rectangle;

public interface Matter {
	public abstract Rectangle getHitbox();
	public abstract void setScroll(int dx,int dy);
}
