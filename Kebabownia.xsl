<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <body>
    <h1>Kababownia!</h1>
    <h3>Pracownicy:</h3>
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

    <h3>Produkty:</h3>
    <table>
        <tr>
            <td><b>Nazwa</b></td>
            <td>Cena</td>
            <td>Skladniki</td>
        </tr>
    <xsl:for-each select="kebabownia/produkty/produkt">
        <tr style="color:brown">
            <td><b><xsl:value-of select="nazwa"/></b></td>
            <td><u><xsl:value-of select="cena"/></u></td>
            <td>
            <ol>
                <xsl:for-each select="skladniki/skladnik">
                <xsl:sort select="ilosc" data-type="number" order="descending"/> 
                    <li>
                        <xsl:value-of select="nazwa"/>&#160;
                        <xsl:choose>
                            <xsl:when test="ilosc/@jednostka='kg'">
                                <xsl:value-of select="ilosc*1000"/>&#160;
                            </xsl:when>
                            <xsl:when test="ilosc/@jednostka='l'">
                                <xsl:value-of select="ilosc*1000"/>&#160;
                            </xsl:when>
                            <xsl:otherwise> 
                                <xsl:value-of select="ilosc"/>&#160;
                            </xsl:otherwise>
                        </xsl:choose>
                        <xsl:choose>
                            <xsl:when test="ilosc/@jednostka='kg'">g
                            </xsl:when>
                            <xsl:when test="ilosc/@jednostka='l'">ml
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="ilosc/@jednostka"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </li>
                    
                </xsl:for-each>
            </ol>
            </td>
        </tr>
    </xsl:for-each>
    </table>
    
  </body>
  </html>
</xsl:template>


</xsl:stylesheet>
