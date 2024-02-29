package ru.perm.v.el59.office.iproviders.web;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.web.CommentDocW;
import ru.perm.v.el59.office.db.web.DocW;

public interface ICommentDocWProvider extends IGenericDao<CommentDocW, Long> {
   List<CommentDocW> getByDocW(DocW var1);

   void deleteByDocW(DocW var1);
}
