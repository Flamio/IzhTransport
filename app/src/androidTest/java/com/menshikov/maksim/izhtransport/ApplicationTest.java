package com.menshikov.maksim.izhtransport;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import android.test.ApplicationTestCase;


import com.menshikov.maksim.izhtransport.Sources.ITransportInfoSource;
import com.menshikov.maksim.izhtransport.Transport.TransportParser;
import com.menshikov.maksim.izhtransport.map.IMapSource;
import com.menshikov.maksim.izhtransport.map.MapModel;

import java.io.IOException;
import java.util.ArrayList;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application>
{
    public ApplicationTest()
    {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    public void testTransportParser() throws IOException, InterruptedException
    {
        TransportParser tp = new TransportParser(new ITransportInfoSource() {
            @Override
            public void setTransportParameters(int transportId, int number) {

            }

            @Override
            public String getServerResponse() {
                return "<script>\n" +
                        "   if (doc_layers['layer-215']!=undefined) {doc_layers['layer-215'].removeAll();}\n" +
                        "   doc_layers['layer-215']=new ymaps.GeoObjectCollection();\n" +
                        "   doc_layers['layer-215'].options['tip']='layer';\n" +
                        "   doc_layers['layer-215'].mapvisible=true;\n" +
                        "   doc_layers['layer-215'].modevisible=[];\n" +
                        "  myPlacemark = new ymaps.Placemark([56.8371, 53.2031], {id: '20000410',balloonContentBody: '<small>№10, 2110 (20)</small>', hintContent: '<small>№10, 2110 (20)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/90.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000410', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000410') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8748, 53.28], {id: '20000195',balloonContentBody: '<small>№10, 2043 (12)</small>', hintContent: '<small>№10, 2043 (12)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/stop.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000195', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000195') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8715, 53.2229], {id: '20000275',balloonContentBody: '<small>№2, 1247 (6)</small>', hintContent: '<small>№2, 1247 (6)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/360.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000275', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000275') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8318, 53.1851], {id: '20000388',balloonContentBody: '<small>№10, 2079 (13)</small>', hintContent: '<small>№10, 2079 (13)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/90.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000388', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000388') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8753, 53.2812], {id: '20000442',balloonContentBody: '<small>№10, 2150 (14)</small>', hintContent: '<small>№10, 2150 (14)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/105.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000442', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000442') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8836, 53.2505], {id: '20000283',balloonContentBody: '<small>№7, 1262 (4)</small>', hintContent: '<small>№7, 1262 (4)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/off.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000283', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000283') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8515, 53.2916], {id: '20000339',balloonContentBody: '<small>№2, 1335 (10)</small>', hintContent: '<small>№2, 1335 (10)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/stop.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000339', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000339') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8815, 53.1919], {id: '20000450',balloonContentBody: '<small>№9, 2164 (7)</small>', hintContent: '<small>№9, 2164 (7)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/315.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000450', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000450') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.846, 53.2172], {id: '20000362',balloonContentBody: '<small>№4, 1358 (3)</small>', hintContent: '<small>№4, 1358 (3)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/255.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000362', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000362') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8552, 53.211], {id: '20000304',balloonContentBody: '<small>№1, 1291 (7)</small>', hintContent: '<small>№1, 1291 (7)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/345.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000304', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000304') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8876, 53.2408], {id: '20000331',balloonContentBody: '<small>№2, 1326 (4)</small>', hintContent: '<small>№2, 1326 (4)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/stop.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000331', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000331') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8351, 53.2444], {id: '20000312',balloonContentBody: '<small>№2, 1299 (1)</small>', hintContent: '<small>№2, 1299 (1)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/75.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000312', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000312') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8619, 53.2213], {id: '20000268',balloonContentBody: '<small>№2, 1223 (3)</small>', hintContent: '<small>№2, 1223 (3)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/195.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000268', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000268') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8768, 53.2693], {id: '20000327',balloonContentBody: '<small>№4, 1317 (8)</small>', hintContent: '<small>№4, 1317 (8)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/285.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000327', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000327') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8459, 53.2142], {id: '20000359',balloonContentBody: '<small>№7, 1355 (10)</small>', hintContent: '<small>№7, 1355 (10)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/stop.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000359', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000359') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8916, 53.2629], {id: '20000325',balloonContentBody: '<small>№1, 1313 (6)</small>', hintContent: '<small>№1, 1313 (6)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/255.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000325', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000325') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8358, 53.281], {id: '20000397',balloonContentBody: '<small>№10, 2091 (1)</small>', hintContent: '<small>№10, 2091 (1)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/180.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000397', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000397') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8386, 53.2811], {id: '20000355',balloonContentBody: '<small>№2, 1351 (5)</small>', hintContent: '<small>№2, 1351 (5)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/stop.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000355', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000355') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8754, 53.2804], {id: '20000456',balloonContentBody: '<small>№10, 2172 (6)</small>', hintContent: '<small>№10, 2172 (6)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/270.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000456', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000456') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8329, 53.1964], {id: '20000040',balloonContentBody: '<small>№14, 2149 (11)</small>', hintContent: '<small>№14, 2149 (11)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/255.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000040', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000040') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8339, 53.1216], {id: '20000387',balloonContentBody: '<small>№10, 2078 (17)</small>', hintContent: '<small>№10, 2078 (17)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/285.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000387', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000387') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8458, 53.2138], {id: '20000346',balloonContentBody: '<small>№4, 1342 (5)</small>', hintContent: '<small>№4, 1342 (5)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/165.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000346', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000346') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8549, 53.198], {id: '20000443',balloonContentBody: '<small>№9, 2151 (4)</small>', hintContent: '<small>№9, 2151 (4)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/180.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000443', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000443') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8387, 53.2286], {id: '20000041',balloonContentBody: '<small>№14, 2126 (8)</small>', hintContent: '<small>№14, 2126 (8)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/255.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000041', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000041') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8379, 53.2199], {id: '20000514',balloonContentBody: '<small>№6, 2094 (7)</small>', hintContent: '<small>№6, 2094 (7)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/270.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000514', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000514') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8498, 53.2253], {id: '20000305',balloonContentBody: '<small>№2, 1292 (2)</small>', hintContent: '<small>№2, 1292 (2)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/345.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000305', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000305') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8827, 53.2259], {id: '20000347',balloonContentBody: '<small>№7, 1343 (6)</small>', hintContent: '<small>№7, 1343 (6)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/210.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000347', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000347') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8327, 53.1109], {id: '20000377',balloonContentBody: '<small>№10, 2065 (5)</small>', hintContent: '<small>№10, 2065 (5)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/stop.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000377', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000377') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8338, 53.2006], {id: '20000449',balloonContentBody: '<small>№9, 2163 (12)</small>', hintContent: '<small>№9, 2163 (12)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/240.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000449', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000449') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8307, 53.1467], {id: '20000441',balloonContentBody: '<small>№10, 2148 (4)</small>', hintContent: '<small>№10, 2148 (4)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/255.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000441', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000441') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8376, 53.2079], {id: '20000370',balloonContentBody: '<small>№10, 2058 (21)</small>', hintContent: '<small>№10, 2058 (21)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/270.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000370', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000370') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8343, 53.2806], {id: '20000328',balloonContentBody: '<small>№2, 1318 (9)</small>', hintContent: '<small>№2, 1318 (9)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/210.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000328', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000328') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8814, 53.2425], {id: '20000303',balloonContentBody: '<small>№7, 1290 (11)</small>', hintContent: '<small>№7, 1290 (11)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/60.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000303', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000303') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8814, 53.2423], {id: '20000336',balloonContentBody: '<small>№4, 1332 (1)</small>', hintContent: '<small>№4, 1332 (1)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/off.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000336', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000336') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.868, 53.2079], {id: '20000320',balloonContentBody: '<small>№7, 1308 (3)</small>', hintContent: '<small>№7, 1308 (3)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/180.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000320', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000320') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8695, 53.2163], {id: '20000365',balloonContentBody: '<small>№7, 1361 (8)</small>', hintContent: '<small>№7, 1361 (8)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/90.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000365', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000365') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8874, 53.2398], {id: '20000292',balloonContentBody: '<small>№2, 1275 (11)</small>', hintContent: '<small>№2, 1275 (11)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/90.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000292', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000292') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8793, 53.2239], {id: '20000281',balloonContentBody: '<small>№1, 1259 (10)</small>', hintContent: '<small>№1, 1259 (10)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/30.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000281', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000281') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.845, 53.2048], {id: '20000274',balloonContentBody: '<small>№1, 1244 (8)</small>', hintContent: '<small>№1, 1244 (8)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/255.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000274', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000274') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8349, 53.1102], {id: '20000193',balloonContentBody: '<small>№9, 2039 (15)</small>', hintContent: '<small>№9, 2039 (15)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/210.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000193', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000193') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8334, 53.1999], {id: '20000432',balloonContentBody: '<small>№9, 2135 (2)</small>', hintContent: '<small>№9, 2135 (2)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/60.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000432', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000432') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8455, 53.2], {id: '20000386',balloonContentBody: '<small>№9, 2077 (3)</small>', hintContent: '<small>№9, 2077 (3)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/360.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000386', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000386') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.835, 53.1182], {id: '20000448',balloonContentBody: '<small>№9, 2161 (8)</small>', hintContent: '<small>№9, 2161 (8)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/150.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000448', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000448') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8831, 53.2262], {id: '20000044',balloonContentBody: '<small>№14, 2055 (10)</small>', hintContent: '<small>№14, 2055 (10)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/210.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000044', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000044') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8649, 53.2903], {id: '20000043',balloonContentBody: '<small>№14, 2096 (12)</small>', hintContent: '<small>№14, 2096 (12)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/360.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000043', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000043') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8695, 53.2137], {id: '20000360',balloonContentBody: '<small>№4, 1356 (2)</small>', hintContent: '<small>№4, 1356 (2)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/90.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000360', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000360') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.848, 53.2893], {id: '20000356',balloonContentBody: '<small>№7, 1352 (14)</small>', hintContent: '<small>№7, 1352 (14)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/195.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000356', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000356') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8606, 53.2908], {id: '20000396',balloonContentBody: '<small>№10, 2090 (7)</small>', hintContent: '<small>№10, 2090 (7)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/180.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000396', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000396') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8322, 53.1656], {id: '20000383',balloonContentBody: '<small>№9, 2072 (9)</small>', hintContent: '<small>№9, 2072 (9)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/270.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000383', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000383') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8326, 53.111], {id: '20000398',balloonContentBody: '<small>№9, 2092 (13)</small>', hintContent: '<small>№9, 2092 (13)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/stop.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000398', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000398') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8317, 53.1709], {id: '20000437',balloonContentBody: '<small>№10, 2144 (22)</small>', hintContent: '<small>№10, 2144 (22)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/270.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000437', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000437') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8781, 53.1848], {id: '20000416',balloonContentBody: '<small>№9, 2117 (14)</small>', hintContent: '<small>№9, 2117 (14)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/195.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000416', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000416') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8432, 53.286], {id: '20000057',balloonContentBody: '<small>№10, 2041 (10)</small>', hintContent: '<small>№10, 2041 (10)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/15.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000057', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000057') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8344, 53.2344], {id: '20000439',balloonContentBody: '<small>№6, 2146 (6)</small>', hintContent: '<small>№6, 2146 (6)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/165.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000439', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000439') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.832, 53.1641], {id: '20000038',balloonContentBody: '<small>№14, 2162 (4)</small>', hintContent: '<small>№14, 2162 (4)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/75.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000038', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000038') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8778, 53.2223], {id: '20000309',balloonContentBody: '<small>№4, 1296 (7)</small>', hintContent: '<small>№4, 1296 (7)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/195.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000309', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000309') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8763, 53.2733], {id: '20000348',balloonContentBody: '<small>№7, 1344 (5)</small>', hintContent: '<small>№7, 1344 (5)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/285.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000348', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000348') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8615, 53.1832], {id: '20000412',balloonContentBody: '<small>№9, 2112 (11)</small>', hintContent: '<small>№9, 2112 (11)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/165.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000412', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000412') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8465, 53.2303], {id: '20000295',balloonContentBody: '<small>№2, 1281 (7)</small>', hintContent: '<small>№2, 1281 (7)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/165.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000295', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000295') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8766, 53.1911], {id: '20000405',balloonContentBody: '<small>№6, 2103 (3)</small>', hintContent: '<small>№6, 2103 (3)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/75.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000405', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000405') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8672, 53.181], {id: '20000379',balloonContentBody: '<small>№9, 2067 (23)</small>', hintContent: '<small>№9, 2067 (23)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/360.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000379', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000379') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8307, 53.1486], {id: '20000399',balloonContentBody: '<small>№10, 2093 (15)</small>', hintContent: '<small>№10, 2093 (15)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/75.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000399', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000399') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8563, 53.2919], {id: '20000340',balloonContentBody: '<small>№7, 1336 (2)</small>', hintContent: '<small>№7, 1336 (2)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/345.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000340', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000340') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8357, 53.2484], {id: '20000404',balloonContentBody: '<small>№6, 2101 (5)</small>', hintContent: '<small>№6, 2101 (5)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/270.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000404', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000404') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8604, 53.221], {id: '20000042',balloonContentBody: '<small>№14, 2107 (2)</small>', hintContent: '<small>№14, 2107 (2)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/stop.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000042', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000042') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8863, 53.2322], {id: '20000294',balloonContentBody: '<small>№1, 1279 (5)</small>', hintContent: '<small>№1, 1279 (5)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/255.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000294', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000294') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.832, 53.164], {id: '20000451',balloonContentBody: '<small>№9, 2165 (6)</small>', hintContent: '<small>№9, 2165 (6)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/75.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000451', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000451') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8698, 53.2226], {id: '20000345',balloonContentBody: '<small>№1, 1341 (4)</small>', hintContent: '<small>№1, 1341 (4)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/180.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000345', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000345') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8388, 53.2329], {id: '20000199',balloonContentBody: '<small>№10, 2100 (8)</small>', hintContent: '<small>№10, 2100 (8)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/270.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000199', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000199') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8383, 53.2283], {id: '20000395',balloonContentBody: '<small>№10, 2089 (11)</small>', hintContent: '<small>№10, 2089 (11)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/105.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000395', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000395') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8345, 53.2348], {id: '20000342',balloonContentBody: '<small>№2, 1338 (8)</small>', hintContent: '<small>№2, 1338 (8)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/315.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000342', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000342') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8604, 53.1946], {id: '20000175',balloonContentBody: '<small>№9, 2006 (16)</small>', hintContent: '<small>№9, 2006 (16)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/270.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000175', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000175') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8281, 53.1316], {id: '20000045',balloonContentBody: '<small>№14, 2073 (5)</small>', hintContent: '<small>№14, 2073 (5)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/stop.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000045', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000045') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8492, 53.2902], {id: '20000460',balloonContentBody: '<small>№10, 2178 (3)</small>', hintContent: '<small>№10, 2178 (3)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/off.png', iconImageSize: [24, 24], iconImageOffset: [-12, -12],  id: '20000460', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000460') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8571, 53.2101], {id: '20000307',balloonContentBody: '<small>№7, 1294 (12)</small>', hintContent: '<small>№7, 1294 (12)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/165.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000307', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000307') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8663, 53.208], {id: '20000311',balloonContentBody: '<small>№1, 1298 (2)</small>', hintContent: '<small>№1, 1298 (2)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/180.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000311', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000311') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8403, 53.2007], {id: '20000424',balloonContentBody: '<small>№6, 2127 (1)</small>', hintContent: '<small>№6, 2127 (1)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/180.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000424', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000424') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8401, 53.2806], {id: '20000408',balloonContentBody: '<small>№10, 2108 (2)</small>', hintContent: '<small>№10, 2108 (2)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/180.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000408', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000408') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8327, 53.2784], {id: '20000014',balloonContentBody: '<small>№10, 2045 (18)</small>', hintContent: '<small>№10, 2045 (18)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/75.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000014', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000014') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8793, 53.258], {id: '20000036',balloonContentBody: '<small>№14, 2170 (13)</small>', hintContent: '<small>№14, 2170 (13)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/180.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000036', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000036') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.832, 53.2687], {id: '20000270',balloonContentBody: '<small>№4, 1231 (4)</small>', hintContent: '<small>№4, 1231 (4)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/270.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000270', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000270') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.8647, 53.2899], {id: '20000301',balloonContentBody: '<small>№7, 1287 (1)</small>', hintContent: '<small>№7, 1287 (1)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/165.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000301', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000301') });doc_layers['layer-215'].add(myPlacemark);myPlacemark = new ymaps.Placemark([56.87, 53.1804], {id: '20000434',balloonContentBody: '<small>№6, 2140 (4)</small>', hintContent: '<small>№6, 2140 (4)</small>'},\n" +
                        "  { iconLayout: 'default#image', iconImageHref: 'http://ico.igis.ru/gortrans/troll/green/180.png', iconImageSize: [44, 44], iconImageOffset: [-22, -22],  id: '20000434', visible: true,mode: '2', balloonPanelMaxMapArea: 0 });\n" +
                        " myPlacemark.events.add('balloonopen', function (e) { loadurl('#cmd','/layers/layer_item.php?layer=215&item=20000434') });doc_layers['layer-215'].add(myPlacemark);iMap.geoObjects.add(doc_layers['layer-215']);</script>\n" +
                        "  <script>\n" +
                        "   if (timeout_215 != undefined) {\n" +
                        "    clearTimeout(timeout_215);\n" +
                        "   }\n" +
                        "   var timeout_215 = setTimeout(function() {loadurl('#cmd','/layers/?param=filter=IMode,=,2&id=215&editor=0&reload=1&');}, 10000);\n" +
                        "  </script>\n";
            }
        });

        ArrayList<Location> loc = tp.getTransportPositions(0, 0);

        assertTrue(loc.size() == 83);
    }

    public void testConvertionToMap()
    {
        MapModel mm = new MapModel(0,0,new IMapSource() {
            @Override
            public int getHeight() {
                return 1920;
            }

            @Override
            public int getWidth() {
                return 1080;
            }

            @Override
            public Bitmap getMap(Rect rect, int screenWidth, int screenHeight) {
                return null;
            }

            @Override
            public Bitmap getBadMap(Rect rect, int screenWidth, int screenHeight) {
                return null;
            }
        });

        Location loc = new Location("izh");
        loc.setLongitude(56.990728);
        loc.setLatitude(52.915183);

        Point p = mm.convertLocationToMap(loc);

        assertTrue(p.x == 0);
        assertTrue(p.y == 0);

        Location loc2 = new Location("izh");
        loc2.setLatitude(56.98);
        loc2.setLongitude(53);

        Point p2 = mm.convertLocationToMap(loc2);

        assertTrue(p2.x>=0);
        assertTrue(p2.y>=0);

        assertTrue(p2.x != 0);
        assertTrue(p2.y != 0);

        Location loc3 = new Location("izh");
        loc3.setLongitude(56.710817);
        loc3.setLatitude(53.557886);

        Point p3 = mm.convertLocationToMap(loc3);

        assertTrue(p3.x == 1080);
        assertTrue(p3.y == 1920);

    }

    public void testPointInRect()
    {
        MapModel mm = new MapModel(100,100,new IMapSource() {
            @Override
            public int getHeight() {
                return 1080;
            }

            @Override
            public int getWidth() {
                return 1920;
            }

            @Override
            public Bitmap getMap(Rect rect, int screenWidth, int screenHeight) {
                return null;
            }

            @Override
            public Bitmap getBadMap(Rect rect, int screenWidth, int screenHeight) {
                return null;
            }
        });

        mm.setCurrentTop(300);
        mm.setCurrentLeft(300);
        assertFalse(mm.isPointInCurrentMapRect(new Point(0,0)));
        assertTrue(mm.isPointInCurrentMapRect(new Point(300,300)));
        assertFalse(mm.isPointInCurrentMapRect(new Point(1920,1080)));
        assertTrue(mm.isPointInCurrentMapRect(new Point(350,350)));
        assertTrue(mm.isPointInCurrentMapRect(new Point(400,400)));
        assertFalse(mm.isPointInCurrentMapRect(new Point(401,400)));
    }

}