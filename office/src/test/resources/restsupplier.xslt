<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="utf-8"
		indent="yes" />

	<!-- <xsl:template match="/rests"> <xsl:apply-templates select="rest/cena"/> 
		</xsl:template> <xsl:template match="cena"> <xsl:text> cena=</xsl:text> <xsl:value-of 
		select="."/> </xsl:template> -->
	<xsl:template match="/">
		<xsl:for-each select="rests/rest" >
			<xsl:sort select="cena" data-type="number" order="ascending"/>
			<xsl:text> cena=</xsl:text>
			<xsl:value-of select="cena" />
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>

