<#macro print_groupt g idx>
	<Группа>
		<Ид>${g.cod?html}</Ид>
		<Наименование>${g.name?html}</Наименование>
		<Родитель>${g.parent.cod}</Родитель>
		<СопутствующиеТовары>
		<#list g.relationTovar  as tovar>
			<Ид>${tovar.nnum}<Ид>
		</#list>
		</СопутствующиеТовары>
		<Индекc>${idx}</Индекc>
		<Группы>
		<#list g.childs as child>
			<@print_groupt child child?index/>
		</#list>
		</Группы>
	</Группа>
</#macro>


<#macro print_tovarinfo t>
	<#if t.tovar.group.groupT??>
	<Товар>
		<Ид>${t.nnum?string["0"]}</Ид>
		<Наименование>${t.tovar.nameFull?html}</Наименование>
		<КраткоеОписание>${t.info?html}</КраткоеОписание>
		<БазоваяЕдиница>
			<cod>1</cod>
			<name>Штука</name>
			<val>шт.</val>
		</БазоваяЕдиница>
		<ТорговаяМарка>${t.tovar.brand?html!""}</ТорговаяМарка>
		<ХарактеристикиТовара>
		<#list t.listFeature as f>
			<ХарактеристикаТовара>
				<Наименование>${f.grp?html!""};${f.name?html!""}</Наименование>
				<Значение>${f.oldval?html!""}</Значение>
				<Индекс>${f?index}</Индекс>
				<Основная>${f.prmry?c}</Основная>
				<#if f.prmry>
					<НаименованиеДляПоиска>${f.name?html}</НаименованиеДляПоиска>
					<ЗначениеДляПоиска>${f.val?html}</ЗначениеДляПоиска>
					<ИндексОсновной>${f.positionPrmry?html}</ИндексОсновной>
				</#if>
			</ХарактеристикаТовара>
		</#list>
		</ХарактеристикиТовара>
		<Картинки>
		<#list t.listPhoto as p>
			<Картинка>
				<Наименование>${(p.name?html)}</Наименование>
				<Файл>${p.path?html}</Файл>
				<Индекс>${p?index}</Индекс>
			</Картинка>
		</#list>
		</Картинки>
		<Группы>
			<Код>${t.tovar.group.groupT.cod}</Код>
		</Группы>
		<#-- Разборки лидер продаж или нет -->
		<#assign isLider="Нет">
		<#assign dateChanged=t.tovar.dateChanged>
		<#if liders?seq_contains(t.tovar.nnum)>
			<#assign isLider="Да">
			<#assign dateChanged=.now>
		</#if>
		<ДатаИзменения>${dateChanged?string["dd.MM.yyyy HH:mm"]}</ДатаИзменения>
		<Лидер>${isLider}</Лидер>
	</Товар>
	</#if>
</#macro>
<?xml version="1.0" encoding="UTF-8"?>
<import><Классификатор>
<Ид>1</Ид>
<Наименование>Классификатор (Каталог товаров)</Наименование>
<Группы>
<#list groupt.childs as g>
	<@print_groupt g g?index/> 
</#list>
</Группы>
</Классификатор><Классификатор>
<Ид>1</Ид>
<Наименование>Каталог товаров</Наименование>
<Товары>
<#list listTovarInfo as t>
	<@print_tovarinfo t/>
</#list>
</Товары>
</Классификатор>
</import>