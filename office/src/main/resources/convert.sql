alter table db.tovar add column name character varying(255);

update db.tovar set name=description;

ALTER TABLE db.move ALTER COLUMN qty type numeric(19,2),
ALTER COLUMN summain type numeric(19,2),
ALTER COLUMN summaout type numeric(19,2);

alter table db.bonuscard alter column ddate type date;

ALTER TABLE db.bonuscardmove ALTER COLUMN summa type numeric(19,2);

ALTER TABLE db.bonusk 
ALTER COLUMN mink type numeric(19,2),
ALTER COLUMN maxk type numeric(19,2);

alter table db.discount alter column fromdate type date,alter column todate type date;

alter table db.doc alter column ddate type date,alter column datereceive type date;

ALTER TABLE db.doc 
ALTER COLUMN volume type numeric(19,2),
ALTER COLUMN summa type numeric(19,2);

ALTER TABLE db.docdetail 
ALTER COLUMN qty type numeric(19,2),
ALTER COLUMN summain type numeric(19,2),
ALTER COLUMN summaout type numeric(19,2),
ALTER COLUMN summaend type numeric(19,2),
ALTER COLUMN k1 type numeric(19,2),
ALTER COLUMN k2 type numeric(19,2),
ALTER COLUMN price type numeric(19,2)
;

ALTER TABLE db.docfile
ALTER COLUMN summa type numeric(19,2)
;

ALTER TABLE db.docitem
ALTER COLUMN cena type numeric(19,2),
ALTER COLUMN qty type numeric(19,2),
ALTER COLUMN qtyzakaz type numeric(19,2)
;

ALTER TABLE db.doctitle
ALTER COLUMN summain type numeric(19,2),
ALTER COLUMN summainold type numeric(19,2),
ALTER COLUMN summaout type numeric(19,2),
ALTER COLUMN k2 type numeric(19,2),
ALTER COLUMN k1 type numeric(19,2)
;

alter table db.doctitle alter column ddatecreate type date;

ALTER TABLE db.docw
ALTER COLUMN summa type numeric(19,2)
;

ALTER TABLE db.docwitem
ALTER COLUMN qty type numeric(19,2),
ALTER COLUMN cena type numeric(19,2)
;

alter table db.docwitem alter column ddateplanout type date;

ALTER TABLE db.dolgnost
ALTER COLUMN tarif type numeric(19,2)
;

alter table db.fileimage alter column ddate type date;

alter table db.files 
alter column data type date,
alter column mindate type date,
alter column maxdate type date,
alter column dwrite type date
;

ALTER TABLE db.grouptovar
ALTER COLUMN factor type numeric(19,2),
ALTER COLUMN volume type numeric(19,2),
ALTER COLUMN minnacenka type numeric(19,2),
ALTER COLUMN nacenkaforsite type numeric(19,2)
;

alter table db.lopt 
alter column ddate type date
;

ALTER TABLE db.move
ALTER COLUMN qty type numeric(19,2),
ALTER COLUMN summain type numeric(19,2),
ALTER COLUMN summaout type numeric(19,2)
;

ALTER TABLE db.paycredit
ALTER COLUMN firstpay type numeric(19,2),
ALTER COLUMN summacredit type numeric(19,2)
;

ALTER TABLE db.payment
alter column ddate type date
;

ALTER TABLE db.payment
ALTER COLUMN summa type numeric(19,2),
ALTER COLUMN k1 type numeric(19,2),
ALTER COLUMN k2 type numeric(19,2)
;

ALTER TABLE db.plan
ALTER COLUMN kmarga type numeric(19,2),
ALTER COLUMN kto type numeric(19,2),
ALTER COLUMN plansummain type numeric(19,2),
ALTER COLUMN plansummaout type numeric(19,2),
ALTER COLUMN summain type numeric(19,2),
ALTER COLUMN summaout type numeric(19,2),
ALTER COLUMN fond type numeric(19,2),
ALTER COLUMN acc type numeric(19,2),
ALTER COLUMN pds type numeric(19,2),
ALTER COLUMN planhours type numeric(19,2),
ALTER COLUMN planmarga type numeric(19,2),
ALTER COLUMN accin type numeric(19,2),
ALTER COLUMN pdsin type numeric(19,2),
ALTER COLUMN planqtysmen type numeric(19,2),
ALTER COLUMN planpercentacc type numeric(19,2),
ALTER COLUMN planpercentpds type numeric(19,2)
;

ALTER TABLE db.plan
alter column ddate type date
;

ALTER TABLE db.plandownload
ALTER COLUMN summa type numeric(19,2)
;

ALTER TABLE db.plandownloadsum
ALTER COLUMN plan type numeric(19,2)
;

ALTER TABLE db.podcardrange
ALTER COLUMN min type numeric(19,2),
ALTER COLUMN max type numeric(19,2),
ALTER COLUMN summa type numeric(19,2)
;

ALTER TABLE db.price
ALTER COLUMN cena type numeric(19,2),
ALTER COLUMN k type numeric(19,2)
;

ALTER TABLE db.pricecategory
ALTER COLUMN min type numeric(19,2),
ALTER COLUMN max type numeric(19,2)
;

ALTER TABLE db.reestrdoc
ALTER COLUMN summa type numeric(19,2),
ALTER COLUMN summapay type numeric(19,2),
ALTER COLUMN summaagree type numeric(19,2)
;

ALTER TABLE db.rest
ALTER COLUMN qty type numeric(19,2),
ALTER COLUMN cenain type numeric(19,2)
;

ALTER TABLE db.restcur
ALTER COLUMN qty type numeric(19,2),
ALTER COLUMN freeqty type numeric(19,2),
ALTER COLUMN cenain type numeric(19,2)
;

ALTER TABLE db.restforplan
ALTER COLUMN summaall type numeric(19,2),
ALTER COLUMN percent type numeric(19,2),
ALTER COLUMN summain type numeric(19,2)
;

ALTER TABLE db.restsupplier
ALTER COLUMN cena type numeric(19,2),
ALTER COLUMN qty type numeric(19,2)
;

ALTER TABLE db.restsupplier
alter column ddate type date
;

ALTER TABLE db.restweb
ALTER COLUMN cena type numeric(19,2),
ALTER COLUMN qty type numeric(19,2)
;

ALTER TABLE db.restweb
alter column ddate type date
;

ALTER TABLE db.shop
ALTER COLUMN space type numeric(19,2)
;

ALTER TABLE db.tdocact
alter column ddate type date
;

ALTER TABLE db.tdoccallmaster
alter column ddate type date,
alter column ddateresult type date
;

ALTER TABLE db.tdoccallmaster
ALTER COLUMN summa type numeric(19,2)
;

ALTER TABLE db.tdocfromsupplier
alter column ddate type date
;

ALTER TABLE db.tdocobmen
alter column ddate type date
;

ALTER TABLE db.tdocorder
alter column ddate type date
;

ALTER TABLE db.tdocorder
ALTER COLUMN summa type numeric(19,2)
;

ALTER TABLE db.tdocreceivefromsc
alter column ddate type date
;

ALTER TABLE db.tdocrequestmanager
alter column ddate type date,
alter column ddateresult type date
;

ALTER TABLE db.tdocsendtosc
alter column ddate type date
;

ALTER TABLE db.tdocstatementcustomer
alter column ddate type date
;

ALTER TABLE db.tdoctocustomer
alter column ddate type date
;

ALTER TABLE db.tdoctosc
alter column ddate type date
;

ALTER TABLE db.tdoctoshop
alter column ddate type date
;

ALTER TABLE db.tdoctosupplier
alter column ddate type date
;

ALTER TABLE db.tovar
ALTER COLUMN cena0 type numeric(19,2),
ALTER COLUMN cenainrub type numeric(19,2),
ALTER COLUMN cenainue type numeric(19,2)
;

ALTER TABLE db.tovarbonus
ALTER COLUMN percent type numeric(19,2)
;

ALTER TABLE db.typepds
ALTER COLUMN cena type numeric(19,2),
ALTER COLUMN min type numeric(19,2),
ALTER COLUMN max type numeric(19,2)
;

ALTER TABLE db.typesert
ALTER COLUMN cost type numeric(19,2)
;

ALTER TABLE db.userzp
ALTER COLUMN summaacc type numeric(19,2),
ALTER COLUMN summabonus type numeric(19,2),
ALTER COLUMN summapds type numeric(19,2),
ALTER COLUMN summa20kop type numeric(19,2),
ALTER COLUMN qtysmen type numeric(19,2),
ALTER COLUMN summaaccin type numeric(19,2),
ALTER COLUMN summapdsin type numeric(19,2),
ALTER COLUMN summabonusin type numeric(19,2),
ALTER COLUMN summaaccbonus type numeric(19,2),
ALTER COLUMN summapdsbonus type numeric(19,2),
ALTER COLUMN summaaccbonusall type numeric(19,2),
ALTER COLUMN summabonusforaccpds type numeric(19,2)
;

