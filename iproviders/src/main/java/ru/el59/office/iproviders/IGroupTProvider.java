package ru.el59.office.iproviders;

import java.io.IOException;
import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.critery.GroupTCritery;
import ru.el59.office.db.GroupT;

public interface IGroupTProvider extends IGenericDao<GroupT, String> {
   List<GroupT> getByEnd(String var1);

   List<GroupT> getAll();

   GroupT getByCod(String var1);

   GroupT getTree(GroupT var1);

   String getNewCodChildForParent(GroupT var1);

   List<GroupT> getByCritery(GroupTCritery var1);

   GroupT checkGroup(GroupT var1) throws Exception;

   byte[] getImage(String var1) throws IOException;

   void setPhoto(GroupT var1, byte[] var2) throws IOException, Exception;

   GroupT initChilds(GroupT var1);

   GroupT init(GroupT var1);

   void removeGroupFromParent(GroupT var1, GroupT var2) throws Exception;

   GroupT addGroupToParent(GroupT var1, GroupT var2) throws Exception;

   void removeIndexFromParent(int var1, GroupT var2) throws Exception;

   void removeIndexFromParent(int var1, String var2) throws Exception;

   void moveDown(GroupT var1, GroupT var2) throws Exception;

   void moveUp(GroupT var1, GroupT var2) throws Exception;

   void addRelationTovar(String var1, Integer var2) throws Exception;
}
