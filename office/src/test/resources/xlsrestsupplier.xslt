<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:html="http://www.w3.org/TR/REC-html40">

    <!--
        <xsl:output method="xml" version="1.0" encoding="utf-8"
                    indent="yes"/>
    -->

    <!--
        <xsl:output method="html" version="1.0" encoding="utf-8"
                    indent="yes"/>
    -->

    <xsl:output method="html" encoding="utf-8" indent="yes"/>
    <!--<xsl:output method="text"/>-->

    <xsl:key name="tovar-key" match="/rests/rest" use="tovar"/>

    <xsl:variable name="tovars"
                  select="/rests/rest[generate-id(.) = generate-id(key('tovar-key', tovar))]"/>

    <xsl:key name="supplier-key" match="/rests/rest" use="supplier"/>

    <xsl:variable name="suppliers"
                  select="/rests/rest[generate-id(.) = generate-id(key('supplier-key', supplier))]"/>

    <xsl:key name="tovar-supplier" match="/rests/rest"
             use="concat(tovar,supplier)"/>


    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <style>
                </style>
            </head>
            <body>
                <table cellspacing="0" border="1">
                    <tr>

                        <td>
                            <b>Метагруппа</b>
                        </td>
                        <td>
                            <b>Группа</b>
                        </td>
                        <td>
                            <b>Ном.номер</b>
                        </td>
                        <td>
                            <b>Товар</b>
                        </td>
                        <td>
                            <b>Категория</b>
                        </td>
                        <td>
                            <b>Лучшая цена</b>
                        </td>
                        <td>
                            <b>Поставщик лучшей цены</b>
                        </td>
                        <xsl:for-each select="$suppliers">
                            <xsl:sort select="supplier"/>
                            <xsl:call-template name="show_title">
                                <xsl:with-param name="param-suppler" select="supplier"/>
                            </xsl:call-template>
                        </xsl:for-each>
                        <xsl:for-each select="$tovars">
                            <xsl:sort select="root-cod"/>
                            <xsl:sort select="groupTovar-cod"/>
                            <xsl:sort select="tovar"/>
                            <!--Не выгружать Устаревшее-->
                            <xsl:if test="root != 'Устаревшее'">
                                <xsl:call-template name="show_tovar">
                                    <xsl:with-param name="param-tovar" select="."/>
                                </xsl:call-template>
                            </xsl:if>
                        </xsl:for-each>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>

    <!--Заголовок-->
    <xsl:template name="show_title">
        <xsl:param name="param-supplier"/>
        <td>
            <b>
                <xsl:value-of select="supplier"/>
            </b>
        </td>
    </xsl:template>

    <!--Товары-->
    <xsl:template name="show_tovar">
        <xsl:param name="param-tovar"/>
        <!--        Вычисление мин.цены-->
        <xsl:variable name="bestprice">
            <xsl:for-each select="key('tovar-key',$param-tovar/tovar)/cena">
                <xsl:sort data-type="number" order="ascending"/>
                <xsl:if test="position() = 1">
                    <xsl:value-of select="."/>
                </xsl:if>
            </xsl:for-each>
        </xsl:variable>
        <xsl:variable name="bestsupplier">
            <xsl:for-each select="$suppliers">
                <xsl:variable name="var-supplier1" select="supplier"/>
                <xsl:variable name="var-cena"
                              select="sum(key('tovar-supplier',concat($param-tovar/tovar,$var-supplier1))/cena)"/>
                <xsl:if test="$var-cena = $bestprice">
                    <xsl:value-of select="$var-supplier1"/>
                </xsl:if>
            </xsl:for-each>
        </xsl:variable>
        <tr>
            <td>
                <xsl:value-of select="$param-tovar/root"/>
            </td>
            <td>
                <xsl:value-of select="$param-tovar/groupTovar"/>
            </td>
            <td align="right">
                <xsl:value-of select="$param-tovar/nnum"/>
            </td>
            <td>
                <xsl:value-of select="$param-tovar/tovar"/>
            </td>
            <xsl:if test="$param-tovar/category = 15">
                <td bgcolor="#FF3333" align="right">
                    <xsl:value-of select="$param-tovar/category"/>
                </td>
            </xsl:if>
            <xsl:if test="$param-tovar/category != 15">
                <td>-</td>
            </xsl:if>
            <td align="right" sdnum="1049;0;# ##0" bgcolor="#66CCFF">
                <xsl:value-of select="format-number($bestprice,'# ###')"/>
            </td>
            <td bgcolor="#66CCFF">
                <xsl:value-of select="$bestsupplier"/>
            </td>
            <xsl:for-each select="$suppliers">
                <xsl:sort select="supplier"/>

                <xsl:variable name="var-supplier" select="supplier"/>
                <xsl:variable name="var-cena"
                              select="sum(key('tovar-supplier',concat($param-tovar/tovar,$var-supplier))/cena)"/>

                <xsl:if test="$var-cena = $bestprice">
                    <td bgcolor="#FFFF00" align="right" sdnum="1049;0;# ##0">
                        <xsl:value-of select="format-number($var-cena,'0')"/>
                    </td>
                </xsl:if>
                <xsl:if test="$var-cena != $bestprice">
                    <td align="right" sdnum="1049;0;# ##0">
                        <xsl:if test="$var-cena != 0">
                            <xsl:value-of select="format-number($var-cena,'# ###')"/>
                        </xsl:if>
                    </td>
                </xsl:if>
            </xsl:for-each>
        </tr>
    </xsl:template>
</xsl:stylesheet>

