# RatioLayout
RatioLayout 按比例布局，适配屏幕
#####还在为UI只标注IOS 的尺寸而犯愁？
#####讲道理？   和女孩子讲道理，你疯了？ 男的？要不你试试打一顿
#####靠眼力？别开玩笑了，你那电脑屏幕，眼睛会瞎的
#####还在为不同的屏幕 写几套尺寸？ 省省吧，哪有那么浪费时间的

上图<p/>
<img src="images/image1.png" width="500px"/><br>
<img src="images/image2.png" width="500px"/><br>

因为经常用到Relative,所以先重写 RelativeLayout

后来因为Material Design的水波效果又加上了FrameLayout

使用方法：
```xml
    <cn.modificactor.ratiolayout.RatioRelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#00ff00"
        app:h="10"
        app:w="20"
        app:by="width|height" />
```
首先呢，你需要，自己确定高或者宽的具体尺寸
然后呢app:by 这个填入之前确定尺寸的是高还是宽
app:h
app:h
这两个呢，就是宽高比了，直接填入IOS的尺寸也是可以的呀:)

要有别的？
构造方法填入
```java
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout, defStyleAttr, 0);
        //获取参考边
        reference = typedArray.getInt(R.styleable.RatioLayout_by, 0) == 0 ? ReferenceType.WIDTH : ReferenceType.HEIGHT;
        //获取高比例
        ratioHeight = typedArray.getFloat(R.styleable.RatioLayout_h, 1);
        //获取宽比例
        ratioWidth = typedArray.getFloat(R.styleable.RatioLayout_w, 1);
        typedArray.recycle();
```
外面填入
```java
     //获取基准边的尺寸
    int childSpec = reference == ReferenceType.WIDTH ? getMeasuredWidth() : getMeasuredHeight();


    // 如果以宽为基准边则宽不变，高按比例得出具体数值，反之亦然
    setMeasuredDimension(
            (int) (reference == ReferenceType.WIDTH ? childSpec : childSpec * ratioWidth / ratioHeight),
            (int) (reference == ReferenceType.HEIGHT ? childSpec : childSpec * ratioHeight / ratioWidth)
    );
```


就可以了，适用View&ViewGroup
