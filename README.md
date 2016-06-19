# ViewInjectTest

简单View注入工具，编译时注解，支持View Field注解，学习用的，嘿嘿

## 使用方法

### sample_simpleviewinject module 添加依赖

```java
apt project(':simpleviewinject-compiler')
compile project(':simpleviewinject-api')
compile project(':simpleviewinject-annotation')
```

### Activity内

```java
public final class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_bottom)
    TextView mTextView;

    @ViewInject(R.id.btn_bottom)
    Button mButton;

    @ViewInject(R.id.lv_content)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleViewInjector.inject(this);
    }
    ...
}
```

### ViewHolder内

```java
static final class ViewHolder {

    @ViewInject(R.id.tv_item)
    TextView textView;

    ViewHolder(View itemView) {
        impleViewInjector.inject(this, itemView);
    }
}
```

### 博客

[Android 编译时View注入工具的实现](http://blog.csdn.net/wsadjklzxc/article/details/51713910)
