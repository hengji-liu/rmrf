package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select username,firstname,lastname,email,birthday,address,type_ "
					+ "from user where type_='1' or type_='4';";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getString("username"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("email"), rs.getString("address"), Integer.parseInt(rs.getString("type_")));
				user.setBirthday(DateUtil.getDateToDay(rs.getString("birthday")));
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBHelper.realease(rs, psmt);
		}
	}

	@Override
	public List<User> searchUserByName(String keyWord) {
		List<User> users = new ArrayList<>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "SELECT * FROM user WHERE (type_='1' or type_='4') AND "
					+ "(username LIKE ? OR firstname LIKE ? OR lastname LIKE ?);";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, "%" + keyWord + "%");
			psmt.setString(2, "%" + keyWord + "%");
			psmt.setString(3, "%" + keyWord + "%");
			rs = psmt.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getString("username"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("email"), rs.getString("address"));
				user.setBirthday(DateUtil.getDateToDay(rs.getString("birthday")));
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBHelper.realease(rs, psmt);
		}

	}

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

	/**
	 * Img is a bit large, get Image in Separate function
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public byte[] getUserImg(String userName) {
		return new byte[0];
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

}
