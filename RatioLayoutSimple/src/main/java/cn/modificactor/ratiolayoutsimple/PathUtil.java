package cn.modificactor.ratiolayoutsimple;


import android.graphics.Path;

public class PathUtil {
    static float __maxRandomnessOffset = 2;
    static float __roughness = 5;
    static float __bowing = 0.85f;
    static float __curveTightness = 0;
    static float __curveStepCount = 9;


    public static final Path rectangle(int x, int y, int width, int height) {
        x = x + 2;
        y = y + 2;
        width = width - 4;
        height = height - 4;
        Path path = new Path();
        path = lineTo(x, y, x + width, y, path);
        path = lineTo(x + width, y, x + width, y + height, path);
        path = lineTo(x + width, y + height, x, y + height, path);
        path = lineTo(x, y + height, x, y, path);
        return path;
//    const node = this._svgNode("path", { d: path.value })
//        svg.appendChild(node);
//        return node;
    }

    public static final Path lineTo(float x1, float y1, float x2, float y2, Path path) {
        float lengthSq = (float) (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        float offset = 2;
        if (400 > lengthSq) {
            offset = (float) (Math.sqrt(lengthSq) / 10);
        }
        float halfOffset = offset / 2;
        float divergePoint = (float) (0.2 + Math.random() * 0.2);
        float midDispX = __bowing * __maxRandomnessOffset * (y2 - y1) / 200;
        float midDispY = __bowing * __maxRandomnessOffset * (x1 - x2) / 200;
        midDispX = _getOffset(-midDispX, midDispX);
        midDispY = _getOffset(-midDispY, midDispY);

//        float path = existingPath || new WiresPath();
        path.moveTo(x1 + _getOffset(-offset, offset), y1 + _getOffset(-offset, offset));
        path.cubicTo(midDispX + x1 + (x2 - x1) * divergePoint + _getOffset(-offset, offset),
                midDispY + y1 + (y2 - y1) * divergePoint + _getOffset(-offset, offset),
                midDispX + x1 + 2 * (x2 - x1) * divergePoint + _getOffset(-offset, offset),
                midDispY + y1 + 2 * (y2 - y1) * divergePoint + _getOffset(-offset, offset),
                x2 + _getOffset(-offset, offset),
                y2 + _getOffset(-offset, offset)
        );
        path.moveTo(x1 + _getOffset(-halfOffset, halfOffset), y1 + _getOffset(-halfOffset, halfOffset));
        path.cubicTo(midDispX + x1 + (x2 - x1) * divergePoint + _getOffset(-halfOffset, halfOffset),
                midDispY + y1 + (y2 - y1) * divergePoint + _getOffset(-halfOffset, halfOffset),
                midDispX + x1 + 2 * (x2 - x1) * divergePoint + _getOffset(-halfOffset, halfOffset),
                midDispY + y1 + 2 * (y2 - y1) * divergePoint + _getOffset(-halfOffset, halfOffset),
                x2 + _getOffset(-halfOffset, halfOffset),
                y2 + _getOffset(-halfOffset, halfOffset)
        );
        return path;
    }

    private static float _getOffset(float min, float max) {
        return (float) (__roughness * ((Math.random() * (max - min)) + min));
    }

}
