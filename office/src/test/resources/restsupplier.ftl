<#ftl encoding="UTF-8"/>
<rests>
<#list listRestSupplier as r>
    <rest>
        <root-cod>${r.tovar.group.root.cod?html}</root-cod>
        <root>${r.tovar.group.root.name?html}</root>
        <groupTovar-cod>${r.tovar.group.cod?html}</groupTovar-cod>
        <groupTovar>${r.tovar.group.name?html}</groupTovar>
        <category>${r.tovar.category}</category>
        <nnum>${r.tovar.nnum?string("0")}</nnum>
        <tovar>${r.tovar.name?html}</tovar>
        <supplier_n>${r.contragent.n}</supplier_n>
        <supplier>${r.contragent.name?html}</supplier>
        <cena>${r.cena?string("0.00")?replace(",",".")}</cena>
    </rest>
</#list>
</rests>
