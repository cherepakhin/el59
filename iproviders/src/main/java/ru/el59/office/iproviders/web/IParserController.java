package ru.el59.office.iproviders.web;

import java.io.IOException;
import javax.xml.xpath.XPathExpressionException;
import ru.el59.office.db.Manager;

public interface IParserController {
   String parseTovar(Integer var1, String var2, Manager var3, Boolean var4, Boolean var5) throws XPathExpressionException, IOException, Exception;
}
