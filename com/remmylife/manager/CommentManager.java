package com.remmylife.manager;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.remmylife.dbacess.*;
import com.remmylife.diary.*;

public class CommentManager extends Manager {


	Comment comment = new Comment();
	public CommentManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentManager(String driver, String url, String user,
			String password) {
		super(driver, url, user, password);
		// TODO Auto-generated constructor stub
	}
	
	public boolean save(Comment comment){
		try {
			dataManager.connectToDatabase();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		int commentid = comment.getCommentid();
		int diaryid = comment.getDiaryid();
		int userid = comment.getUserid();
		String content = comment.getContent();
		//String savecomment2= "insert into `commentlist`(`commentid`,`diaryid`,`userid`,`content`) values ('"+commentid+"', '"+ diaryid+"', '" + userid +"', '"+content+");" ;
		String savecomment="insert into `commentlist`(`userid`,`diaryid`,`content`) values ('"
				+ userid+"', '" + diaryid +"', '"+content+"');" ;
		String updatecomment = "update `commentlist` SET `userid` ="+ userid +", `diaryid`="+diaryid+", `content`= '"+content+"' ;";
        if(commentid==0){
        	try {
				dataManager.setUpdate(savecomment);
				dataManager.disconnectFromDatabase();
			} catch (IllegalStateException | SQLException e) {
				e.printStackTrace();
				return false;
			}
		}else{
        	try {
				dataManager.setUpdate(updatecomment);
				dataManager.disconnectFromDatabase();
			} catch (IllegalStateException | SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
		
	}
	
	public boolean delete(Comment comment){
		try {
			dataManager.connectToDatabase();
			int commentid = comment.getCommentid();
			String del ="delete from commentlist where commentid ="+ commentid;
			dataManager.setUpdate(del);
			dataManager.disconnectFromDatabase();
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	public boolean deleteList(ArrayList<Comment> ids){
		for(int i = 0 ; i < ids.size() ; i ++){
			delete(ids.get(i));
		}
		return false;
	}
	
	
	public ArrayList<Comment> getCommentList(Diary diary){
		int diaryid = diary.getId();
		String getlist = "select * from Comment where diaryid =" +diaryid+";";
		return  execSqlQuery(getlist);
		
	}

	@Override
	public ArrayList<Comment> constructList(DataManager dataManager){
		ArrayList<Comment> commentList= new ArrayList<Comment>();
		try {
			dataManager.connectToDatabase();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		int numberOfRow= dataManager.getRowCount();
		for(int i =0; i<numberOfRow; i++){
			String ts = dataManager.getValueAt(i,0).toString();
			comment.setCommentid(Integer.valueOf(ts));

			String ss=(String)dataManager.getValueAt(i,1);						
			comment.setUserid(Integer.valueOf(ss));
			
			String ds = dataManager.getValueAt(i,2).toString();
			comment.setDiaryid(Integer.valueOf(ds));
			
			comment.setContent((String)dataManager.getValueAt(i, 3));
						
			commentList.add(new Comment(comment));//�ò����½�һ��class
	
		}
		dataManager.disconnectFromDatabase();
		return commentList;
	}
	
	
	public ArrayList<Comment> execSqlQuery(String query){
		try {
			dataManager.connectToDatabase();
			dataManager.setQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Comment> commentList = constructList(dataManager);
		dataManager.disconnectFromDatabase();
		return commentList;
	}
	
}
