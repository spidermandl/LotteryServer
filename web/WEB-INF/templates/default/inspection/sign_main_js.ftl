<script type="text/javascript" src="/assets/js/baidu/convertor.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=67af81f989735445e5772e87d91e517f"></script>
<#--<script type="text/javascript" src="http://api.map.baidu.com/geocoder/v2/?&ak=SEtr1pq8BKg5MIHe7EgKhL3H"></script>-->
<script>
    $(function () {
        navigator.geolocation.getCurrentPosition(translatePoint); //定位
    });
    function translatePoint(position) {
        var currentLat = position.coords.latitude;
        var currentLon = position.coords.longitude;
        var gpsPoint = new BMap.Point(currentLon, currentLat);
        BMap.Convertor.translate(gpsPoint, 0, initMap); //转换坐标
    }
    function initMap(point) {
//初始化地图
        map = new BMap.Map("map");
        map.addControl(new BMap.NavigationControl());
        map.addControl(new BMap.ScaleControl());
        map.addControl(new BMap.OverviewMapControl());
        map.centerAndZoom(point, 15);
        map.addOverlay(new BMap.Marker(point))
    }
</script>
</script>
