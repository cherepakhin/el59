package ru.perm.v.el59.office.iproviders.web;

import java.io.IOException;
import java.util.List;
import javax.xml.xpath.XPathExpressionException;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Tovar;
import ru.el59.office.db.TovarInfo;

public interface IParserEl {
   TovarInfo parseTovar(Integer var1, Manager var2) throws XPathExpressionException, IOException, Exception;

   List<Tovar> parseListTovar(List<Integer> var1, Manager var2) throws XPathExpressionException, IOException, Exception;
}
