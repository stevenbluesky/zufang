jello-demo
==========

Jello demo &amp; doc, you can [preview this online](http://106.186.23.103:8080/).

## 如何使用

1. 安装 jello

    ```
    npm install -g jello
    ```
2. 安装插件

    ```
    npm install -g fis-parser-marked
    npm install -g fis-parser-utc
    npm install -g fis-parser-sass
    npm install -g fis-packager-depscombine
    npm install -g fis-postprocessor-am
    ```
3. git clone 下来此仓库

    ```
    git clone https://github.com/2betop/jello-demo.git
    ```
4. 进入 jello-demo 目录后 安装 components

    ```
    cd jello-demo
    jello install
    ```
5. 进入当前目录后发布代码

    ```
    jello release
    jello server start
    ```
6. 自动打开浏览器预览页面


## 无限瀑布流写法

1.将分页 写在 UrlController 里 传递page、rows
    @Resource
    private PositionInfoService positionInfoService;
    

    public void setPositionInfoService(PositionInfoService positionInfoService) {
        this.positionInfoService = positionInfoService;
    }
    @RequestMapping(value = "/list.html")
    public ModelAndView list(@ModelAttribute("positionInfoVo") PositionInfoVo positionInfoVo) {
        // 退出
        Grid grid = positionInfoService.findPositionInfoPage(positionInfoVo, positionInfoVo.getPage(), 
                positionInfoVo.getRows());
        Integer page =  positionInfoVo.getPage();
        Integer rows = positionInfoVo.getRows();
        ModelMap modelMap = new ModelMap();
        modelMap.put("gird", grid);
        modelMap.put("isNoNext", true);
        if(Math.ceil((double)grid.getCount() / rows) <= page){
            modelMap.put("isNoNext", false);
        }
        modelMap.put("page",page + 1);
        modelMap.put("rows", rows);
        return new ModelAndView("page/index/list", modelMap);
    } 
2.显示页vm页面写法
    
    <div id="container" class="f-cb">
    </div>
    <a class="infinite-more-link" href="list.html?page=1&rows=10">更多</a>
3.列表页VM写法
    <div class="infinite-container">
       <!--  #set( $list = [26, 27, 21, 28, 20, 25, 16, 21, 20, 25, 16, 21, 28, 20, 25, 15, 23, 22, 16])
        #foreach( $item in $list)
            <div class="infinite-item">
                <div class="imgholder">
                    <img class="lazy" data-original="http://www.inwebson.com/demo/blocksit-js/demo2/images/img${item}.jpg"/>
                </div>
                <strong>Sunset Lake ${page}</strong>
                <p>A peaceful sunset view...</p>
                <div class="meta">by j osborn</div>
            </div>
        #end -->
        #foreach( $item in $gird.list )
           <div class="infinite-item">
                <div class="imgholder">
                    <img class="lazy" data-original="http://www.inwebson.com/demo/blocksit-js/demo2/images/img${item}.jpg"/>
                </div>
                <strong>Sunset Lake</strong>
                <p>A peaceful sunset view...</p>
                <div class="meta">by j osborn</div>
            </div>
        #end
    </div>
    #if($!isNoNext )
        <a class="infinite-more-link" href="list.html?page=$page&rows=$rows">更多</a>
    #end
4.使用插件
    require('waypoints');
    require('waypoints/infinite');
    require('blocksit/blocksit');
    require('imgLoader/imgLoader');
    var infinite = new Waypoint.Infinite({
        element: $('#container'),
        onAfterPageLoad: function() {
            $("img.lazy").lazyload({
                // effect: "fadeIn",
                // 如果一个img元素没有指定src属性，我们使用这个placeholder，在真正的图片被加载之前，此img会呈现这个占位图。
                placeholder_data_img: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC',

                // IE6/7 的 placeholder。这是我当时在百度时的cdn地址，建议改为你服务器上的任一张灰色或白色的1*1的小图
                placeholder_real_img: 'http://webmap3.map.bdimg.com/yyfm/lazyload/0.0.1/img/placeholder.png',
                load: function() {}
            });
            $('#container').BlocksIt({
                numOfCol: 5,
                offsetX: 8,
                offsetY: 8
            });
        }
    })