package daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Config.ServiceConfig;
import bean.LoginLog;
import bean.Pager;
import bean.User;
import daoIterface.RecordCountDao;
import daoIterface.UserDao;
import util.DBHelper;
import util.DateUtil;
import util.SQLReplaceUtil;

/**
 * Created by Linus on 10/09/2016.
 */
public class UserDaoImpl implements UserDao {
	private static final String USERNAME = "username";
	private static final String PS = "ps";
	private static final String TYPE_ = "type_";
	private static final String FIRSTNAME = "firstname";
	private static final String LASTNAME = "lastname";
	private static final String EMAIL = "email";
	private static final String BIRTHDAY = "birthday";
	private static final String ADDRESS = "address";
	private static final String CREDITCARD = "creditcard";
	private static final String USER = "user";
	private static final String ONE = "1";

	@Override
	public User getUserByUserName(String userName) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from user where username=? and (type_='1' or type_ = '4');";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userName);
			rs = psmt.executeQuery();
			if (rs.next()) {
				User user = new User(rs.getString("username"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("email"), rs.getString("address"), Integer.parseInt(rs.getString("type_")));
				user.setBirthday(DateUtil.getDateToDay(rs.getString("birthday")));
				return user;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBHelper.realease(rs, psmt);
		}
	}

	@Override
	public List<LoginLog> getLoginLog(String userName) {
		List<LoginLog> loginLogList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from `user_login` where username=? order by time desc";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userName);
			rs = psmt.executeQuery();
			while (rs.next()) {
				LoginLog loginLog = new LoginLog();
				loginLog.setUserByName(userName);
				loginLog.setTime(DateUtil.getDateToMin(rs.getString("time")));
				if (rs.getString("granted").equals("0")) {
					loginLog.setGranted(false);
				} else {
					loginLog.setGranted(true);
				}
				loginLogList.add(loginLog);
			}
			return loginLogList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBHelper.realease(rs, psmt);
		}

	}

	@Override
	public User getUserWhenLogin(String username, String ps) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "SELECT * FROM " + USER//
					+ " " + "WHERE " + TYPE_ + "='" + ONE + "' AND"//
					+ " " + USERNAME + "=? AND"//
					+ " " + PS + "=?;";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, username);
			psmt.setString(2, ps);
			rs = psmt.executeQuery();
			if (rs.next()) {
				User u = new User();
				int type_ = rs.getInt(TYPE_);
				String fn = rs.getString(FIRSTNAME);
				String ln = rs.getString(LASTNAME);
				String em = rs.getString(EMAIL);
				String bd = rs.getString(BIRTHDAY);
				String vn = rs.getString(ADDRESS);
				String cc = rs.getString(CREDITCARD);
				u.setUsername(username);
				u.setPs(ps);
				u.setType_(type_);
				u.setFirstname(fn);
				u.setLastname(ln);
				u.setEmail(em);
				u.setBirthday(bd);
				u.setAddress(vn);
				u.setCreditcard(cc);
				return u;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBHelper.realease(rs, psmt);
		}
	}

	public User getUserWhenConfirm(String username) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "SELECT * FROM " + USER//
					+ " " + "WHERE " + TYPE_ + "='" + ONE + "' AND"//
					+ " " + USERNAME + "=?;";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, username);
			rs = psmt.executeQuery();
			if (rs.next()) {
				User u = new User();
				int type_ = rs.getInt(TYPE_);
				String ps = rs.getString(PS);
				String fn = rs.getString(FIRSTNAME);
				String ln = rs.getString(LASTNAME);
				String em = rs.getString(EMAIL);
				String bd = rs.getString(BIRTHDAY);
				String vn = rs.getString(ADDRESS);
				String cc = rs.getString(CREDITCARD);
				u.setUsername(username);
				u.setPs(ps);
				u.setType_(type_);
				u.setFirstname(fn);
				u.setLastname(ln);
				u.setEmail(em);
				u.setBirthday(bd);
				u.setAddress(vn);
				u.setCreditcard(cc);
				return u;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBHelper.realease(rs, psmt);
		}

	}

	public void confirm(String username) {
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "UPDATE user SET " + TYPE_ + "=" + ONE + " where " + USERNAME + "=?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, username);
			psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.realease(null, psmt);
		}
	}

	public int save(User u) {
		Connection conn = null;
		PreparedStatement psmt = null;
		int newRowCount = 0;
		try {
			conn = DBHelper.getConnection();
			String sql = "INSERT INTO " + USER + " VALUES (?,?,?,?,?,?,?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, u.getUsername());
			psmt.setString(2, u.getPs());
			psmt.setInt(3, u.getType_());
			psmt.setString(4, u.getFirstname());
			psmt.setString(5, u.getLastname());
			psmt.setString(6, u.getEmail());
			String[] bd = u.getBirthday().split("-");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date udate = format.parse(u.getBirthday());
			long t = udate.getTime();
			Date sdate = new java.sql.Date(t);
			psmt.setDate(7, sdate);
			psmt.setString(8, u.getAddress());
			psmt.setString(9, u.getCreditcard());
			newRowCount = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.realease(null, psmt);
		}
		return newRowCount;
	}

	@Override
	public int update(User u) {
		Connection conn = null;
		PreparedStatement psmt = null;
		int newRowCount = 0;
		try {
			conn = DBHelper.getConnection();
			String sql = "UPDATE " + USER
					+ " SET ps=?, firstname=?, lastname=?, email=?, birthday=?, address=?, creditcard=?"
					+ " WHERE username=?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, u.getPs());
			psmt.setString(2, u.getFirstname());
			psmt.setString(3, u.getLastname());
			psmt.setString(4, u.getEmail());
			String[] bd = u.getBirthday().split("-");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date udate = format.parse(u.getBirthday());
			long t = udate.getTime();
			Date sdate = new java.sql.Date(t);
			psmt.setDate(5, sdate);
			psmt.setString(6, u.getAddress());
			psmt.setString(7, u.getCreditcard());
			psmt.setString(8, u.getUsername());
			newRowCount = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.realease(null, psmt);
		}
		return newRowCount;
	}

	@Override
	public Pager<User> getUserPage(int page_num) {
		// Do count
		RecordCountDao recordCountDao = new RecordCountDao();
		int totalRecords = recordCountDao.recordCount("(*)", "from user where type_='1' or type_='4';");
		// Do search
		List<User> users = new ArrayList<>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select username,firstname,lastname,email,birthday,address,type_ from user where type_='1' or type_='4' LIMIT ?,?;";
			psmt = conn.prepareStatement(sql);
			int offset = (page_num - 1) * ServiceConfig.USER_PAGE_LIMIT;
			psmt.setInt(1, offset);// offset
			psmt.setInt(2, ServiceConfig.USER_PAGE_LIMIT);// end
			rs = psmt.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getString("username"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("email"), rs.getString("address"), Integer.parseInt(rs.getString("type_")));
				user.setBirthday(DateUtil.getDateToDay(rs.getString("birthday")));
				users.add(user);
			}
			return new Pager<>(ServiceConfig.USER_PAGE_LIMIT, page_num, totalRecords, users);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBHelper.realease(rs, psmt);
		}
	}

	@Override
	public Pager<User> searchUser(String keyWord, int page_num) {
		// DO COUNT
		RecordCountDao recordCountDao = new RecordCountDao();
		String queryCount = "FROM user WHERE (type_='1' or type_='4') AND "
				+ "(username LIKE ? OR firstname LIKE ? OR lastname LIKE ?)";
		String str = "\'%" + keyWord + "%\'";
		queryCount = SQLReplaceUtil.replaceQuestionMarks(queryCount, str, str, str);

		int totalRecords = recordCountDao.recordCount("(*)", queryCount);
		// DoSearch
		List<User> users = new ArrayList<>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "SELECT * FROM user WHERE (type_='1' or type_='4') AND "
					+ "(username LIKE ? OR firstname LIKE ? OR lastname LIKE ?) LIMIT ?,?;";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, "%" + keyWord + "%");
			psmt.setString(2, "%" + keyWord + "%");
			psmt.setString(3, "%" + keyWord + "%");

			int offset = (page_num - 1) * ServiceConfig.USER_PAGE_LIMIT;
			psmt.setInt(4, offset);
			psmt.setInt(5, ServiceConfig.USER_PAGE_LIMIT);
			rs = psmt.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getString("username"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("email"), rs.getString("address"));
				user.setBirthday(DateUtil.getDateToDay(rs.getString("birthday")));
				users.add(user);
			}
			return new Pager<>(ServiceConfig.USER_PAGE_LIMIT, page_num, totalRecords, users);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBHelper.realease(rs, psmt);
		}

	}

}
