package daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bean.LoginLog;
import bean.User;
import daoIterface.UserDao;
import util.DBHelper;
import util.DateUtil;

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
				loginLog.setTime(rs.getString("time"));
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
}
