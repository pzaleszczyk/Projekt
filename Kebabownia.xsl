
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <body>
    <h2>Kababownia!</h2>
    <h1>Pracownicy:</h1>
    <table>
        <tr>
            <td>Imie</td>
            <td>Nazwisko</td>
            <td>Pozycja</td>
        </tr>
        <xsl:for-each select="kebabownia/lokal/pracownicy/pracownik">
				<xsl:if test="@plec='K'">
					<tr style="color:red">
						<td><b><xsl:value-of select="imie"/></b></td>
						<td><u><xsl:value-of select="nazwisko"/></u></td>
						<td><xsl:value-of select="pozycja"/></td>
					</tr>
				</xsl:if>
				<xsl:if test="@plec='M'">
					<tr style="color:blue">
						<td><b><xsl:value-of select="imie"/></b></td>
						<td><u><xsl:value-of select="nazwisko"/></u></td>
						<td><xsl:value-of select="pozycja"/></td>
					</tr>
				</xsl:if>
        </xsl:for-each>
    </table>

   
    <h1>Produkty:</h1>
    <table>
        <tr>
            <td><b>Nazwa</b></td>
            <td>Cena</td>
            <td>Skladniki</td>
        </tr>
    <xsl:for-each select="kebabownia/lokal/pracownicy/pracownik">
        <tr style="color:brown">
            <td><b><xsl:value-of select="nazwa"/></b></td>
            <td><u><xsl:value-of select="cena"/></u></td>
            <td>
                <xsl:value-of select="skladniki/skladnik/nazwa"/> 
                <xsl:value-of select="skladniki/skladnik/ilosc"/> 
                <xsl:value-of select="skladniki/skladnik/nazwa/attribute::jednostka"/>
            </td>
        </tr>
    </xsl:for-each>
    </table>
    
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>

