<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" encoding="utf-8"
                indent="yes"/>

    <!--
        <xsl:output method="html" version="1.0" encoding="utf-8"
                    indent="yes"/>
    -->

    <!--        <xsl:output method="html" encoding="utf-8" indent="yes"/>-->
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
        <rests>
            <suppliers>
                <xsl:for-each select="$suppliers">
                    <xsl:sort select="supplier"/>
                    <xsl:call-template name="show_title">
                        <xsl:with-param name="param-suppler" select="supplier"/>
                    </xsl:call-template>
                </xsl:for-each>
            </suppliers>

            <tovars>
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
            </tovars>
        </rests>
    </xsl:template>

    <!--Заголовок-->
    <xsl:template name="show_title">
        <xsl:param name="param-supplier"/>
        <supplier>
            <xsl:value-of select="supplier"/>
        </supplier>
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
        <tovar>
            <root>
                <cod>
                    <xsl:value-of select="$param-tovar/root-cod"/>
                </cod>
                <name>
                    <xsl:value-of select="$param-tovar/root"/>
                </name>
            </root>
            <group>
                <cod>
                    <xsl:value-of select="$param-tovar/groupTovar-cod"/>
                </cod>
                <name>
                    <xsl:value-of select="$param-tovar/groupTovar"/>
                </name>
            </group>
            <nnum>
                <xsl:value-of select="$param-tovar/nnum"/>
            </nnum>
            <name>
                <xsl:value-of select="$param-tovar/tovar"/>
            </name>
            <category>
                <xsl:value-of select="$param-tovar/category"/>
            </category>

            <bestprice>
                <xsl:value-of select="format-number($bestprice,'0')"/>
            </bestprice>

            <bestsupplier>
                <xsl:value-of select="$bestsupplier"/>
            </bestsupplier>

            <prices>
                <xsl:for-each select="$suppliers">
                    <xsl:sort select="supplier"/>

                    <xsl:variable name="var-supplier" select="supplier"/>
                    <xsl:variable name="var-cena"
                                  select="sum(key('tovar-supplier',concat($param-tovar/tovar,$var-supplier))/cena)"/>
                    <price>
                        <supplier>
                            <xsl:value-of select="$var-supplier"/>
                        </supplier>
                        <cena>
                            <xsl:value-of select="format-number($var-cena,'0')"/>
                        </cena>
                        <isbest>
                            <xsl:if test="$var-cena = $bestprice">
                                <xsl:text>true</xsl:text>
                            </xsl:if>
                            <xsl:if test="$var-cena != $bestprice">
                                <xsl:if test="$var-cena != 0">
                                    <xsl:text>false</xsl:text>
                                </xsl:if>
                            </xsl:if>
                        </isbest>
                    </price>
                </xsl:for-each>
            </prices>
        </tovar>
    </xsl:template>
</xsl:stylesheet>

