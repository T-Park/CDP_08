/*
 * SQLforCC.java
 * 
 * it define sql stmt for Modong
*/

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.spi.DirStateFactory.Result;
import javax.print.attribute.standard.PrinterIsAcceptingJobs;
import javax.xml.ws.FaultAction;

import ProblemDomain.LogList;
import ProblemDomain.DonationOrgnz;
import ProblemDomain.DonationOrgnzAdmin.dItem;
import ProblemDomain.GroupUser;
import ProblemDomain.ModongUser;
import ProblemDomain.SyncData;
import util.BarcodeNumberGenerator;

public class SQLforModong extends CommonSQL {
	// private Statement stmt; // statement
	//
	// private Statement getStatement(Connection conn)
	// {
	// stmt = null;
	// try {
	// stmt = conn.createStatement();
	//
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// System.err.println("create Stmt Error");
	// e.printStackTrace();
	// } finally {
	// return stmt; // return stmt
	// }
	// }

	// #ModongLogin%id%pw
	// for performance it return users uid and save it on the client thread
	public int processLogin(Connection conn, String id, String pw) {
		int res = -1;
		PreparedStatement pstmt;
		String query = "select usrid from usr where id = ? and pw = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			ResultSet rs = pstmt.executeQuery();
			// check result is valid
			if (rs.isBeforeFirst()) {
				rs.next();
				res = rs.getInt("usrid"); // get uid
			}
			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return res;
		}
	}

	// get groupinfo by uid
	// it dose not check for validity about uid
	public GroupUser getGroupInfobyUid(Connection conn, int uid) {
		GroupUser gUser = null;
		PreparedStatement pstmt;
		String query = "select * from groupusr where gid in ( select gid from usr where usrid = ?)";

		String groupName, groupBarcode;
		int groupPoint;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uid);
			ResultSet rs = pstmt.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				groupName = rs.getString("groupname");
				groupPoint = rs.getInt("grouppoint");
				groupBarcode = rs.getString("barcode");

				gUser = new GroupUser();
				gUser.setGroup_name(groupName);
				gUser.setGroup_point(groupPoint);
				gUser.setGroup_barcode(groupBarcode);
			}
			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return gUser; // return group info
		}
	}

	// check user by id
	// return true if exist else false
	public boolean checkUserByID(Connection conn, String id) {
		PreparedStatement pstmt;
		String query = "select count(*) from usr where id = ?";
		int res = -1;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			res = rs.getInt("count(*)");

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return true;
			else
				return false;
		}
	}

	// get new uid
	public int getNewUid(Connection conn) {
		int res = -1;

		PreparedStatement pstmt = null;
		String query = "select count(*) from usr"; // count로 셈
		try {
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			res = rs.getInt("count(*)");
			System.out.println("res: " + res);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// problem, if error, it return false
			return res; // return num of saveuselist
		}
	}

	// join new user
	// #ModongJoin%id%pw%name%job%age%tel
	public boolean processJoin(Connection conn, ModongUser user) {
		PreparedStatement pstmt;
		String query = "insert into usr values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, null )";
		// uid, id, pw, type, joindate, point, name, job, age, donatepoint, tel,
		// barcode,
		int res = -1;

		// set info
		user.setUid(getNewUid(conn) + 1); // set uid
		System.out.println("uid: " + user.getUid());
		user.setDefaultType(user.getUser_age());
		System.out.println("type: " + user.getUser_type());
		user.setUser_startDate(new Date());
		user.setUser_point(0);
		user.setUser_donatePoint(0);
		user.setUser_barcode(BarcodeNumberGenerator.generateBarcodeNum(false));
		System.out.println("type: " + user.getUser_barcode());
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user.getUid());
			pstmt.setString(2, user.getUser_id());
			pstmt.setString(3, user.getUser_pw());
			pstmt.setString(4, user.getUser_type());
			pstmt.setTimestamp(5, new Timestamp(user.getUser_startDate().getTime()));
			pstmt.setInt(6, user.getUser_point());
			pstmt.setString(7, user.getUser_name());
			pstmt.setString(8, user.getUser_job());
			pstmt.setInt(9, user.getUser_age());
			pstmt.setInt(10, user.getUser_donatePoint());
			pstmt.setString(11, user.getUser_tel());
			pstmt.setString(12, user.getUser_barcode());
			// pstmt.setInt(13, user.getUser_groupCode()); // 0 means no group

			res = pstmt.executeUpdate();
			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return true; // Success to join
			else
				return false; // Fail to join
		}

	}

	// get userinfo
	public ModongUser getUserInfoByIdPw(Connection conn, String input_id, String input_pw) {
		PreparedStatement pstmt;
		String query = "select * from usr where id = ? and pw = ?";
		ModongUser user = null;
		int usrid, point, age, donatepoint, gid;
		String id, pw, name, job, tel, barcode, type;
		Date joinDate;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, input_id);
			pstmt.setString(2, input_pw);
			ResultSet rs = pstmt.executeQuery();

			// check null
			if (rs.isBeforeFirst()) {
				rs.next();
				usrid = rs.getInt("usrid");
				id = rs.getString("id");
				pw = rs.getString("pw");
				type = rs.getString("type");
				joinDate = rs.getDate("joindate");
				name = rs.getString("name");
				point = rs.getInt("point");
				donatepoint = rs.getInt("donatepoint");
				job = rs.getString("job");
				age = rs.getInt("age");
				tel = rs.getString("tel");
				barcode = rs.getString("barcode");
				gid = rs.getInt("gid");
				user = new ModongUser();
				user.setUid(usrid);
				user.setUser_id(id);
				user.setUser_pw(pw);
				user.setUser_type(type);
				user.setUser_startDate(joinDate);
				user.setUser_name(name);
				user.setUser_point(point);
				user.setUser_job(job);
				user.setUser_age(age);
				user.setUser_donatePoint(donatepoint);
				user.setUser_tel(tel);
				user.setUser_barcode(barcode);
				user.setUser_groupCode(gid);
			}

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return user;
		}
	}

	// #ModongModify%id%pw%name%job%age%tel
	public boolean processModify(Connection conn, ModongUser user) {
		int res = -1;
		PreparedStatement pstmt;
		String query = "update usr set pw=?, name=?, job=?, age=?, tel=? where id = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUser_pw());
			pstmt.setString(2, user.getUser_name());
			pstmt.setString(3, user.getUser_job());
			pstmt.setInt(4, user.getUser_age());
			pstmt.setString(5, user.getUser_tel());
			pstmt.setString(6, user.getUser_id());

			res = pstmt.executeUpdate();

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return true; // Success to modify
			else
				return false; // Fail to modify
		}
	}

	// add donatepoint
	public boolean DonatePointbyID(Connection conn, int amount, String id) {

		PreparedStatement pstmt = null;
		int res = -1;
		String query = "update usr set donatepoint = donatepoint + ? where id=?"; // count로
																					// 셈
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, amount);
			pstmt.setString(2, id);
			res = pstmt.executeUpdate();

			System.out.println("dp res: " + res);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// problem, if error, it return false
			if (res > 0)
				return true; // success to save
			else
				return false; // fail to save
		}
	}

	// get gid by gname
	public int getdidbyDname(Connection conn, String dName) {
		PreparedStatement pstmt;
		String query = "select did from organization where name=?";
		int res = -1;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dName);
			ResultSet rs = pstmt.executeQuery();
			// check null
			if (rs.isBeforeFirst()) {
				rs.next();
				res = rs.getInt("did");
			}

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return res;
		}
	}

	// return use list
	public ArrayList<LogList> getUseList(Connection conn, String param, QueryParameter paramType) {
		PreparedStatement pstmt;
		String query = "";
		switch (paramType) {
		case ID:
			query = "select l.paydate, l.amount, p.name from saveuselist l, store p, usr u where u.usrid = l.usrid and p.pid = l.pid and l.type = 'u' and u.usrid = ?";
			break;
		default:
			System.out.println("타입에러");
			break;
		}
		ArrayList<LogList> arrList = new ArrayList<LogList>();

		int uid = getUidbyParam(conn, param, paramType);
		System.out.println("uid: " + uid);

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uid);
			ResultSet rs = pstmt.executeQuery();

			Date when;
			int amount;
			String where;

			while (rs.next()) {
				LogList list = new LogList();
				when = rs.getDate("paydate");
				amount = rs.getInt("amount");
				where = rs.getString("name");
				list.setWhere(where);
				list.setWhen(when);
				list.setPoint(amount);

				arrList.add(list);
			}

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return arrList;
		}
	}

	// return donation list
	public ArrayList<LogList> getDonationList(Connection conn, String param, QueryParameter paramType) {
		PreparedStatement pstmt;
		String query = "";
		switch (paramType) {
		case ID:
			query = "select d.donatedate, d.amount, o.name from donatelist d, organization o, usr u where u.usrid = d.usrid and o.did = d.did and u.usrid = ?";
			break;
		default:
			System.out.println("타입에러");
			break;
		}
		ArrayList<LogList> arrList = new ArrayList<LogList>();

		int uid = getUidbyParam(conn, param, paramType);
		System.out.println("uid: " + uid);

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uid);
			ResultSet rs = pstmt.executeQuery();

			Date when;
			int amount;
			String where;

			while (rs.next()) {
				LogList list = new LogList();
				when = rs.getDate("donatedate");
				amount = rs.getInt("amount");
				where = rs.getString("name");
				list.setWhere(where);
				list.setWhen(when);
				list.setPoint(amount);

				arrList.add(list);
			}

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return arrList;
		}
	}

	// check is already group in
	public boolean checkExistGroup(Connection conn, String param, QueryParameter parmType) {
		PreparedStatement pstmt;
		int res = -1;
		String query = "";
		switch (parmType) {
		case ID:
			query = "select gid from usr where id=?";
			break;
		default:
			System.out.println("파라미터 에러 >> checkExistGroup");
			break;
		}

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, param);
			ResultSet rs = pstmt.executeQuery();
			// check null
			rs.next();
			res = rs.getInt("gid");

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return true;
			else
				return false;
		}
	}

	// get donate log's new primary key
	public int getNewGroupId(Connection conn) {
		int res = -1;

		PreparedStatement pstmt = null;
		String query = "select count(*) from groupusr"; // count로 셈
		try {
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			res = rs.getInt("count(*)");
			System.out.println("group count res: " + res);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// problem, if error, it return false
			return res; // return num of saveuselist
		}
	}

	public int createNewGroup(Connection conn, String groupName) {
		PreparedStatement pstmt;
		int res = -1;
		String query = "insert into groupusr values (?, ?, ?, ?)";
		int gid = getNewGroupId(conn) + 1;
		System.out.println("new groupid: " + gid);

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, gid);
			pstmt.setString(2, groupName);
			pstmt.setInt(3, 0);
			pstmt.setString(4, BarcodeNumberGenerator.generateBarcodeNum(true)); // create
																					// new
																					// barcode

			res = pstmt.executeUpdate();

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return gid;
			else
				return res;
		}

	}

	// join user to group
	public boolean joinUserToGroup(Connection conn, int gid, String param, QueryParameter paramType) {
		PreparedStatement pstmt;
		int res = -1;
		String query = "";
		switch (paramType) {
		case ID:
			query = "update usr set gid=? where id=?";
			break;
		default:
			System.out.println("파라미터 오류 >> joinUserToGroup");
			break;
		}

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, gid);
			pstmt.setString(2, param);
			res = pstmt.executeUpdate();

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return true;
			return false;
		}
	}

	// #ModongGroupOut%id
	public boolean processGroupOut(Connection conn, String param, QueryParameter paramType) {
		PreparedStatement pstmt;
		int res = -1;
		String query = "";
		switch (paramType) {
		case ID:
			query = "update usr set gid = null where id=?";
			break;
		default:
			System.out.println("파라미터 에러 >> processGroupOut");
			break;
		}

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, param);

			res = pstmt.executeUpdate();

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return true; // Success to group out
			return false; // fail to group out
		}
	}

	// #ModongSyn%id rt
	public SyncData getSyncData(Connection conn, boolean flag, String param, QueryParameter paramType) {
		PreparedStatement pstmt;
		String query = "";

		if (flag) {
			switch (paramType) {
			case ID:
				query = "select u.point, u.gid, g.groupname, g.barcode from usr u, groupusr g where (u.gid = g.gid and u.id =?)";
				break;
			default:
				System.out.println("파라미터 에러 >> getSyncData");
				break;
			}

			SyncData res = null;
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, param);

				ResultSet rs = pstmt.executeQuery();
				// check null
				if (rs.isBeforeFirst()) {
					rs.next();
					res = new SyncData();
					res.setGid(rs.getInt("gid"));
					res.setPoint(rs.getInt("point"));
					res.setGroupName(rs.getString("groupname"));
					res.setGroupBarcode(rs.getString("barcode"));
				}

				pstmt.close();
				conn.commit();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				return res;
			}
		}
		else
		{
			switch (paramType) {
			case ID:
				query = "select u.point, u.gid from usr u where (u.id =?)";
				break;
			default:
				System.out.println("파라미터 에러 >> getSyncData");
				break;
			}

			SyncData res = null;
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, param);

				ResultSet rs = pstmt.executeQuery();
				// check null
				if (rs.isBeforeFirst()) {
					rs.next();
					res = new SyncData();
					res.setGid(rs.getInt("gid"));
					res.setPoint(rs.getInt("point"));
					res.setGroupName(null);
					res.setGroupBarcode(null);
				}

				pstmt.close();
				conn.commit();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				return res;
			}		
		}
	}
	
	// get organization by index
	public ArrayList<DonationOrgnz> getOrg(Connection conn ) {
		
		PreparedStatement pstmt;
		String query = "select * from organization";
		
		ArrayList<DonationOrgnz> orgList = null;
		int did;
		String name;
		int point;
		String tel;
		char type;

		try {
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.isBeforeFirst())
			{
				orgList = new ArrayList<DonationOrgnz>();
				while(rs.next())
				{
					DonationOrgnz org = new DonationOrgnz();
					org.setDonation_id(rs.getInt("did"));
					org.setDonation_name(rs.getString("name"));
					org.setDonation_point(rs.getInt("point"));
					org.setDonation_tel(rs.getString("tel"));
					org.setDonation_type(rs.getString("type").charAt(0));
					orgList.add(org);
				}
			}
			
			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return orgList; // return
		}
	}	
}
