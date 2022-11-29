package com.tongfu.util;

import com.tongfu.config.ShiroConfig;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {


//        tf_product_statistics();
//        tf_demonstration_statistics();
//        tf_order_item();
//        tf_order();

//        BigDecimal b = new BigDecimal(0.00469871);
//        Double a = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//        System.out.println(a);

        tf_company_statistics();
//        tf_demonstration_statistics_sql();
//        tf_message();



        }

    public static String getSql(int year,int mont, int sn,String province){
        String sql = "INSERT INTO `tf_demonstration_statistics` ( `product`, `member`, `create_date`, `ip`, `iplat`, `iplng`, `nation`, `province`, `city`, `district`, `adcode`, `company_id`, \n" +
                "`area`, `address`, `name`, `hospital`, `department`, `phone`, `mobile`, `content`, `type`, `email`, `is_delete`, `status`, `admin_id`) VALUES('83','3',\n" +
                "'"+year+"-"+mont+"-25 16:51:10','61.50.112.138','39.72684','116.34159','中国','"+province+"','北京市','大兴区','110115','7','91','西红门镇嘉悦广场6-501','任天赐','3','2',NULL,\n" +
                "'13522151165','测试免费试用','0','15935962724@163.com','','0',NULL);";

        sql = "INSERT INTO `tf_order` (`create_date`, `modify_date`, `address`, `amount_paid`, `area_name`, `consignee`, `coupon_discount`, `expire`, `fee`, `freight`, `invoice_title`, " +
                "`is_allocated_stock`, `lock_expire`, `memo`, `offset_amount`, `order_status`, `payment_method_name`, `payment_status`, `phone`, `point`," +
                " `promotion`, `promotion_discount`, `shipping_method_name`, `shipping_status`, `sn`, `tax`, `area`, `member`, `operator`, `payment_method`, " +
                "`shipping_method`, `evaluate`, `is_deleted`, `paid_date`, `refunded_date`, `receiver_address`, `delivery_corp`, `delivery_sn`, `is_member_delete`, " +
                "`is_make_invoice`, `type`, `email`, `case_status`, `order_invoice_id`) VALUES('"+year+"-"+mont+"-21 19:34:14','2021-05-21 19:34:14','家悦广场6-501','0.010000'," +
                "'河北省秦皇岛市海港区','雷克斯','0.000000','2021-05-28 19:34:14','0.000000','0.000000',NULL,'',NULL,NULL,'0.000000','2','支付宝','2','13522151168','0',NULL,'1.000000',''," +
                "'5','"+sn+"','0.000000','76','3',NULL,NULL,NULL,'0','0','2021-05-21 19:34:32',NULL,'家悦广场6-501',NULL,NULL,'','null','1','18605222732@163.com','0',NULL);";

        sql = "INSERT INTO `tf_order_item` (`create_date`, `modify_date`, `full_name`, `is_gift`, `name`, `price`, `quantity`, `return_quantity`, `shipped_quantity`, `sn`," +
                " `thumbnail`, `weight`, `orders`, `product`, `is_deleted`, `company`, `company_receiver`, `adviser_receivre`, `product_purchase_id`, `purchase_name`, " +
                "`member_case_report`, `company_case_report`) VALUES('"+year+"-"+mont+"-24 16:36:26','2021-05-24 16:36:26','IQQA-3D肝脏评估和治疗计划辅助系统©','','IQQA-3D肝脏评估和治疗计划辅助系统©'," +
                "'500.000000','1','0','0','SP1621845385758','/productLogoImg/20210521185655314.jpg','0','"+sn+"','83','0','7',NULL,NULL,'4','1个病例',NULL,NULL);";


        return sql;
    }



    public static void tf_product_statistics(){

        String [] ips = {"60.25.188.64","101.227.131.220","14.104.0.0",""};
        int[] memberIds = {1,3,26,27,28,30,31,32, 33,34, 35,36, 37,38,39,40};
        String [] province = {"北京市","天津市","上海市","重庆市","四川省","陕西省","山西省","河南省","江苏省","吉林省","浙江省"};

        int []productIds = {72 , 83 ,  84 ,  120 , 125   };

        Random ra =new Random();
        for (int memberId : memberIds) {
            for (Integer productId : productIds) {
                 memberId = memberIds[ra.nextInt(memberIds.length)];
                 String city = province[ra.nextInt(province.length)];
                String sql = "insert into `tf_product_statistics` (`product`, `member`, `create_date`, `ip`, `iplat`, `iplng`," +
                        " `nation`, `province`, `city`, `district`,\n" +
                        "        `adcode`, `hits`, `company_id`)\n" +
                        "    values('"+productId+"','"+memberId+"','20"+(15+ra.nextInt(6))+"-"+(1+ra.nextInt(12))+"-"+(1+ra.nextInt(30))+" 15:22:43','117.136.38.161','40.22077','116.23128','中国','"+city+"','"+city+"','','110114','1','7');";
                System.out.println(sql);
            }
        }

    }



    public static void tf_demonstration_statistics(){

        String [] ips = {"60.25.188.64","101.227.131.220","14.104.0.0",""};
        int[] memberIds = {1,3,26,27,28,30,31,32, 33,34, 35,36, 37,38,39,40};
        String [] province = {"北京市","天津市","上海市","重庆市","四川省","陕西省","山西省","河南省","江苏省","吉林省","浙江省"};

        int []productIds = {72 , 83 ,  84 ,  120 , 125   };

        int []years = {2023};

        Random ra =new Random();

        for (int i = 0; i < 300; i++) {

            String city = province[ra.nextInt(province.length)]; //随机省份
            int memberId = memberIds[ra.nextInt(memberIds.length)]; //随机用户
            int productId = productIds[ra.nextInt(productIds.length)];//随机产品
            int year = years[ra.nextInt(years.length)]; //随机15年至21年
            int monts = ra.nextInt(12)+1; //随机月份
            int day = ra.nextInt(30)+1; //随机日期
            String sql = "\n" +
                    "insert into `tf_demonstration_statistics` ( `product`, `member`, `create_date`, `ip`, `iplat`, `iplng`, `nation`," +
                    " `province`, `city`, `district`, `adcode`, `company_id`, `area`, `address`, `name`, `hospital`," +
                    " `department`, `phone`, `mobile`, `content`, `type`, `email`, `is_delete`, `status`, `admin_id`) " +
                    "values('"+productId+"','"+memberId+"','"+year+"-"+monts+"-"+day+" 16:40:14','61.50.112.138','39.72684','116.34159','中国','"+city+"','"+city+"','','110115','7','91','西红门镇嘉悦广场6-501','王凯','1','1',NULL,'13522151165','测试','0','15935962724@163.com','','0',NULL);";
            System.out.println(sql);


        }





    }



    public static void tf_demonstration_statistics_sql(){

        for (int i = 6; i <= 12; i++) {
            Integer days = days(2022, i);
            for (int j = 1; j <=30; j++) {
                Random ra =new Random();
                int i1 = ra.nextInt(20)+1;
                for (int k = 0; k < i1; k++) {
                    String sql = "INSERT INTO `tf_demonstration_statistics` ( `product`, `member`, `create_date`, `ip`, `iplat`, `iplng`, `nation`, " +
                            "`province`, `city`, `district`, `adcode`, `company_id`, `area`, `address`, `name`, `hospital`, `department`," +
                            " `phone`, `mobile`, `content`, `type`, `email`, `is_delete`, `status`, `admin_id`) " +
                            "VALUES('83','3','2022-"+i+"-"+j+" 11:47:18','124.78.13.159','31.22024','121.42394','中国'," +
                            "'上海市','上海市','长宁区','310105','7','37','中山西路','王双瑞','5583','63',NULL,'13522151165','申请试用','1','15935962724@163.com','','0',NULL);";

                    System.out.println(sql);
                }


            }
        }
    }




    public static void tf_message(){

        for (int i = 6; i <= 12; i++) {
            Integer days = days(2022, i);
            for (int j = 1; j <=30; j++) {
                Random ra =new Random();
                int i1 = ra.nextInt(20)+1;
                for (int k = 0; k < i1; k++) {
                    String sql = "insert into `tf_message` (`create_date`, `ip`, `for_message`, `receiver`, `sender`, `is_forward_receiver`, `is_forward_sender`, `operator`, `product_id`, `is_handle`, `company_id`, `email`, `content`) " +
                            "values('2022-"+i+"-"+j+" 10:54:43',NULL,NULL,'8','35','\u0001','\u0001',NULL,'83','b\\'1\\'','7','15935962724@163.com','我想了解，自动化的病灶定位、分割及定量分析');";
                    System.out.println(sql);
                }


            }
        }
    }



    public static void tf_company_statistics(){

        for (int i = 6; i <= 12; i++) {
            Integer days = days(2022, i);
            for (int j = 1; j <=30; j++) {
                Random ra =new Random();
                int i1 = ra.nextInt(20)+1;
                for (int k = 0; k < i1; k++) {
                    String sql = "INSERT INTO `tf_company_statistics` (`member`, `create_date`, `company_id`) VALUES('1','2022-"+i+"-"+j+" 14:32:42','7');";
                    System.out.println(sql);
                }


            }
        }
    }





    public static Integer days(Integer year ,Integer month){
        Scanner scanner = new Scanner(System.in);
        int day=0;
        switch(month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day =31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day =30;
                break;
            case 2:
                if(year%400==0 ||year%4==0&&year%100!=0) {
                    day= 29;
                }else {
                    day =28;
                }
        }
       return day;




    }




    public static void tf_order_item(){

        String [] ips = {"60.25.188.64","101.227.131.220","14.104.0.0",""};
        int[] memberIds = {1,3,26,27,28,30,31,32, 33,34, 35,36, 37,38,39,40};
        String [] province = {"北京市","天津市","上海市","重庆市","四川省","陕西省","山西省","河南省","江苏省","吉林省","浙江省"};

        int []productIds = {72,73,74,75,76,83,84,85,96,109,125};
        String [] productNames = {"IQQA- 3D","IQQA-Chest TomoSynthesis CAD","IQQA 多模态三维影像 肝局部功能全量化分析系统","IQQA 胸片解读分析系统","IQQA- Guide 手术导航系统","IQQA-3D肝脏评估和治疗计划辅助系统©","IQQA-3D肝脏评估和治疗计划增值服务","IQQA®- Guide","ICG 3D-Printed Implants FOR Neurosurgery111","IQQA-Chest TomoSynthesis CAD(培训包)","手术导航系统服务报告"};
        int [] orderIds = {82,84,86,107,132,133,148,151,153,154,155,162,163,166,172,173,182,183,184,185,187,557,558,560,567,586,591,607,618,690,923,1261,1263,1264,1265,1266,1269,1276,1289,1584,1585,1586,1587,1588,1589,1590,1591,1592,1593,1594,1595,1596,1597,1598,1599,1600,1601,1602,1603,1604,1605,1606,1607,1608,1609,1610,1611,1612,1613,1614,1615,1616,1617,1618,1619,1620,1621,1622,1623,1624,1625,1626,1627,1628,1629,1630,1631,1632,1633,1634,1635,1636,1637,1638,1639,1640,1641,1642,1643,1644,1645,1646,1647,1648,1649,1650,1651,1652,1653,1654,1655,1656,1657,1658,1659,1660,1661,1662,1663,1664,1665,1666,1667,1668,1669,1670,1671,1672,1673,1674,1675,1676,1677,1678,1679,1680,1681,1682,1683,1684,1685,1686,1687,1688,1689,1690,1691,1692,1693,1694,1695,1696,1697,1698,1699,1700,1701,1702,1703,1704,1705,1706,1707,1708,1709,1710,1711,1712,1713,1714,1715,1716,1717,1718,1719,1720,1721,1722,1723,1724,1725,1726,1727,1728,1729,1730,1731,1732,1733,1734,1735,1736,1737,1738,1739,1740,1741,1742,1743,1744,1745,1746,1747,1748,1749,1750,1751,1752,1753,1754,1755,1756,1757,1758,1759,1760,1761,1762,1763,1764,1765,1766,1767,1768,1769,1770,1771,1772,1773,1774,1775,1776,1777,1778,1779,1780,1781,1782,1783,1784,1785,1786,1787,1788,1789,1790,1791,1792,1793,1794,1795,1796,1797,1798,1799,1800,1801,1802,1803,1804,1805,1806,1807,1808,1809,1810,1811,1812,1813,1814,1815,1816,1817,1818,1819,1820,1821,1822,1823,1824,1825,1826,1827,1828,1829,1830,1831,1832,1833,1834,1835,1836,1837,1838,1839,1840,1841,1842,1843,1844,1845,1846,1847,1848,1849,1850,1851,1852,1853,1854,1855,1856,1857,1858,1859,1860,1861,1862,1863,1864,1865,1866,1867,1868,1869,1870,1871,1872,1873,1874,1875,1876,1877,1878,1879,1880,1881,1882,1883,1884,1885,1886,1887,1888,1889,1890,1891,1892,1893,1894,1895,1896,1897,1898,1899,1900,1901,1902,1903,1904,1905,1906,1907,1908,1909,1910,1911,1912,1913,1914,1915,1916,1917,1918,1919,1920,1921,1922,1923,1924,1925,1926,1927,1928,1929,1930,1931,1932,1933,1934,1978,1979,1980,1997,1999,2000,2037,2042};

        int []years = {2021};

        Random ra =new Random();

        for (int i = 0; i < 600; i++) {
            String city = province[ra.nextInt(province.length)]; //随机省份
            int memberId = memberIds[ra.nextInt(memberIds.length)]; //随机用户
            int a = ra.nextInt(productIds.length);
            int productId = productIds[a];//随机产品
            String productName = productNames[a];
            int year = years[ra.nextInt(years.length)]; //随机15年至21年
            int monts = ra.nextInt(12)+1; //随机月份
            int day = ra.nextInt(30)+1; //随机日期
            int quantity = ra.nextInt(300)+1;
            double price = (ra.nextInt(10)+1)*0.01;
            int orderId = orderIds[ra.nextInt(orderIds.length)];
            String sql =  "insert into `tf_order_item` ( `create_date`, `modify_date`, `full_name`, `is_gift`, `name`, " +
                    "`price`, `quantity`, `return_quantity`, `shipped_quantity`, `sn`, `thumbnail`, `weight`, `orders`, " +
                    "`product`, `is_deleted`, `company`, `company_receiver`, `adviser_receivre`, `product_purchase_id`, " +
                    "`purchase_name`, `member_case_report`, `company_case_report`) values('"+year+"-"+monts+"-"+day+" 00:00:00','2021-05-21 19:34:14'," +
                    "'"+productName+"','','"+productName+"','"+price+"','"+quantity+"','"+quantity+"','0','SP1621596854136'," +
                    "'/productLogoImg/20210521185655314.jpg','0','"+orderId+"','"+productId+"','0','7',NULL,NULL,'82','1个病例',NULL,NULL);" ;
            System.out.println(sql);
        }
    }



    public static void tf_order(){

        String [] ips = {"60.25.188.64","101.227.131.220","14.104.0.0",""};
        int[] memberIds = {1,3,26,27,28,30,31,32, 33,34, 35,36, 37,38,39,40};
        String [] province = {"北京市","天津市","上海市","重庆市","四川省","陕西省","山西省","河南省","江苏省","吉林省","浙江省"};
        String [] paymontys = {"支付宝","微信","银联"};

        int []productIds = {72,73,74,75,76,83,84,85,96,109,125};
        String [] productNames = {"IQQA- 3D","IQQA-Chest TomoSynthesis CAD","IQQA 多模态三维影像 肝局部功能全量化分析系统","IQQA 胸片解读分析系统","IQQA- Guide 手术导航系统","IQQA-3D肝脏评估和治疗计划辅助系统©","IQQA-3D肝脏评估和治疗计划增值服务","IQQA®- Guide","ICG 3D-Printed Implants FOR Neurosurgery111","IQQA-Chest TomoSynthesis CAD(培训包)","手术导航系统服务报告"};
        int [] orderIds = {82,84,86,107,132,133,148,151,153,154,155,162,163,166,172,173,182,183,184,185,187,557,558,560,567,586,591,607,618,690,923,1261,1263,1264,1265,1266,1269,1276,1289,1584,1585,1586,1587,1588,1589,1590,1591,1592,1593,1594,1595,1596,1597,1598,1599,1600,1601,1602,1603,1604,1605,1606,1607,1608,1609,1610,1611,1612,1613,1614,1615,1616,1617,1618,1619,1620,1621,1622,1623,1624,1625,1626,1627,1628,1629,1630,1631,1632,1633,1634,1635,1636,1637,1638,1639,1640,1641,1642,1643,1644,1645,1646,1647,1648,1649,1650,1651,1652,1653,1654,1655,1656,1657,1658,1659,1660,1661,1662,1663,1664,1665,1666,1667,1668,1669,1670,1671,1672,1673,1674,1675,1676,1677,1678,1679,1680,1681,1682,1683,1684,1685,1686,1687,1688,1689,1690,1691,1692,1693,1694,1695,1696,1697,1698,1699,1700,1701,1702,1703,1704,1705,1706,1707,1708,1709,1710,1711,1712,1713,1714,1715,1716,1717,1718,1719,1720,1721,1722,1723,1724,1725,1726,1727,1728,1729,1730,1731,1732,1733,1734,1735,1736,1737,1738,1739,1740,1741,1742,1743,1744,1745,1746,1747,1748,1749,1750,1751,1752,1753,1754,1755,1756,1757,1758,1759,1760,1761,1762,1763,1764,1765,1766,1767,1768,1769,1770,1771,1772,1773,1774,1775,1776,1777,1778,1779,1780,1781,1782,1783,1784,1785,1786,1787,1788,1789,1790,1791,1792,1793,1794,1795,1796,1797,1798,1799,1800,1801,1802,1803,1804,1805,1806,1807,1808,1809,1810,1811,1812,1813,1814,1815,1816,1817,1818,1819,1820,1821,1822,1823,1824,1825,1826,1827,1828,1829,1830,1831,1832,1833,1834,1835,1836,1837,1838,1839,1840,1841,1842,1843,1844,1845,1846,1847,1848,1849,1850,1851,1852,1853,1854,1855,1856,1857,1858,1859,1860,1861,1862,1863,1864,1865,1866,1867,1868,1869,1870,1871,1872,1873,1874,1875,1876,1877,1878,1879,1880,1881,1882,1883,1884,1885,1886,1887,1888,1889,1890,1891,1892,1893,1894,1895,1896,1897,1898,1899,1900,1901,1902,1903,1904,1905,1906,1907,1908,1909,1910,1911,1912,1913,1914,1915,1916,1917,1918,1919,1920,1921,1922,1923,1924,1925,1926,1927,1928,1929,1930,1931,1932,1933,1934,1978,1979,1980,1997,1999,2000,2037,2042};

        int []years = {2015,2016,2017,2018,2019,2020};

        Random ra =new Random();

        for (int i = 0; i < 10000; i++) {
            String city = province[ra.nextInt(province.length)]; //随机省份
            int memberId = memberIds[ra.nextInt(memberIds.length)]; //随机用户
            int a = ra.nextInt(productIds.length);
            int productId = productIds[a];//随机产品
            String productName = productNames[a];
            int year = years[ra.nextInt(years.length)]; //随机15年至21年
            int monts = ra.nextInt(12)+1; //随机月份
            int day = ra.nextInt(30)+1; //随机日期
            int quantity = ra.nextInt(300)+1;
            double price = (ra.nextInt(10)+1)*0.01;
            int orderId = orderIds[ra.nextInt(orderIds.length)];
            String paymonty = paymontys[ra.nextInt(paymontys.length)];
            String sql = "insert into `tf_order` ( `create_date`, `modify_date`, `address`, `amount_paid`, `area_name`, `consignee`," +
                    " `coupon_discount`, `expire`, `fee`, `freight`, `invoice_title`, `is_allocated_stock`, `lock_expire`, " +
                    "`memo`, `offset_amount`, `order_status`, `payment_method_name`, `payment_status`, `phone`, `point`, `promotion`," +
                    " `promotion_discount`, `shipping_method_name`, `shipping_status`, `sn`, `tax`, `area`," +
                    " `member`, `operator`, `payment_method`, `shipping_method`, `evaluate`, `is_deleted`, `paid_date`, " +
                    "`refunded_date`, `receiver_address`, `delivery_corp`, `delivery_sn`, `is_member_delete`, `is_make_invoice`," +
                    " `type`, `email`, `case_status`, `order_invoice_id`, `company_id`, `company_name`) values('"+year+"-"+monts+"-"+day+" 19:34:14'," +
                    "'"+year+"-"+monts+"-"+day+" 19:34:14','家悦广场6-501','0.010000','河北省秦皇岛市海港区','雷克斯','0.000000','"+year+"-"+monts+"-"+day+" 19:35:14','0.000000','0.000000',''," +
                    "'',NULL,NULL,'0.000000','2','"+paymonty+"','2','13522151168','0',NULL,'1.000000','','5','SP16"+year+"68"+monts+"1"+day+"','0.000000','76','3',NULL,NULL,NULL,'0'," +
                    "'0','2021-05-21 19:34:32',NULL,'家悦广场6-501',NULL,NULL,'','\u0001','1','18605222732@163.com','0',NULL,'7',NULL);";
            System.out.println(sql);
        }
    }









}
