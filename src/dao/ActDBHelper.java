/**
 * 
 */
package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import adm.werock.sportstats.basics.Act;
import adm.werock.sportstats.basics.Event;
import adm.werock.sportstats.basics.Player;
import adm.werock.sportstats.basics.Team;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Sergiu Daniel Marsavela This Herlper helps manages the local DB,
 *         named
 */
public class ActDBHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = ActDBHelper.class.getName();
	// DB Name
	private static final String DB_NAME = "ss.db";

	// Acts Table - column names
	private static final String TAG_ACTS = "acts";
	private static final String TAG_AID = "id";
	private static final String TAG_DATE = "date";
	private static final String TAG_UEMAIL = "email_users";
	private static final String TAG_LOCALID = "id_teamlocal";
	private static final String TAG_GUESTID = "id_teamguest";

	// Teams Table - column names
	private static final String TAG_TEAMS = "teams";
	private static final String TAG_TID = "id";
	private static final String TAG_TEAMNAME = "name";
	private static final String TAG_LEAGUEID = "id_leagues";

	// Players Table - column names
	private static final String TAG_PLAYERS = "players";
	private static final String TAG_LICENSE = "licensnumber";
	private static final String TAG_PLAYERNAME = "name";
	private static final String TAG_SURNAME = "surname";
	private static final String TAG_TEAMSID = "id_teams";

	// Events Table - column names
	private static final String TAG_EVENTS = "events";
	private static final String TAG_EID = "id";
	private static final String TAG_ACTSID = "id_acts";
	private static final String TAG_MINUTE = "minute";
	private static final String TAG_TYPE = "type";
	private static final String TAG_VALUE = "value";
	private static final String TAG_PLNUMBER = "licensnumber_players";

	// Acts table create statement
	private static final String CREATE_ACTS = "create table " + TAG_ACTS + "("
			+ TAG_AID + " integer primary key autoincrement, " + TAG_DATE
			+ " DATETIME DEFAULT CURRENT_TIMESTAMP, " + TAG_UEMAIL
			+ " text not null, " + TAG_LOCALID + " INTEGER NOT NULL, "
			+ TAG_GUESTID + " INTEGER NOT NULL);";

	// Teams table create statement
	private static final String CREATE_TEAMS = "create table " + TAG_TEAMS
			+ "(" + TAG_TID + " integer primary key, " + TAG_TEAMNAME
			+ " text not null, " + TAG_LEAGUEID + " INTEGER NOT NULL);";

	// Players table create statement
	private static final String CREATE_PLAYERS = "create table " + TAG_PLAYERS
			+ "(" + TAG_LICENSE + " integer primary key, " + TAG_PLAYERNAME
			+ " text not null, " + TAG_SURNAME + " text not null, "
			+ TAG_TEAMSID + " INTEGER NOT NULL);";

	// Events table create statement
	private static final String CREATE_EVENTS = "create table " + TAG_EVENTS
			+ "(" + TAG_EID + " integer primary key autoincrement, "
			+ TAG_ACTSID + " INTEGER NOT NULL, " + TAG_MINUTE + " INTEGER, "
			+ TAG_TYPE + " text not null, " + TAG_VALUE + " INTEGER, "
			+ TAG_PLNUMBER + " INTEGER NOT NULL);";

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public ActDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		db.execSQL(CREATE_ACTS);
		db.execSQL(CREATE_TEAMS);
		db.execSQL(CREATE_PLAYERS);
		db.execSQL(CREATE_EVENTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TAG_ACTS);
		db.execSQL("DROP TABLE IF EXISTS " + TAG_TEAMS);
		db.execSQL("DROP TABLE IF EXISTS " + TAG_PLAYERS);
		db.execSQL("DROP TABLE IF EXISTS " + TAG_EVENTS);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "acts" table methods ----------------//
	/**
	 * Inser a given Act in the local DB.
	 * 
	 * @param act
	 * @return id of the inserted act
	 */
	public long insertAct(Act act) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		// values.put(TAG_AID, act.getId()); This one is not necessary, as the
		// own DB increments the local ID.
		values.put(TAG_DATE, getDateTime(act.getDate()));
		values.put(TAG_UEMAIL, act.getEmailUser());
		values.put(TAG_LOCALID, act.getIdTeamHome());
		values.put(TAG_GUESTID, act.getIdTeamGuest());

		// insert row
		long act_id = db.insertWithOnConflict(TAG_ACTS, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);

		// Return the id of the inserted Act
		return act_id;
	}

	/**
	 * Pull an Act with the given ID
	 * 
	 * @param act_id
	 * @return the requested Act
	 */
	public Act selectAct(int act_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TAG_ACTS + " WHERE " + TAG_AID
				+ " = " + act_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		int id = c.getInt(c.getColumnIndex(TAG_AID));
		String dateString = c.getString(c.getColumnIndex(TAG_DATE));
		String email = c.getString(c.getColumnIndex(TAG_UEMAIL));
		int idLocal = c.getInt(c.getColumnIndex(TAG_LOCALID));
		int idGuest = c.getInt(c.getColumnIndex(TAG_GUESTID));

		return new Act(id, putDateTime(dateString), email, idLocal, idGuest);
	}

	/**
	 * Pull all acts that a certain user has made.
	 * 
	 * @param user
	 * @return list of user's Acts
	 */
	public ArrayList<Act> selectAct(String user) {

		ArrayList<Act> actsList = new ArrayList<Act>();
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TAG_ACTS + " WHERE "
				+ TAG_UEMAIL + " = " + user;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				int id = c.getInt(c.getColumnIndex(TAG_AID));
				String dateString = c.getString(c.getColumnIndex(TAG_DATE));
				String email = c.getString(c.getColumnIndex(TAG_UEMAIL));
				int idLocal = c.getInt(c.getColumnIndex(TAG_LOCALID));
				int idGuest = c.getInt(c.getColumnIndex(TAG_GUESTID));

				Act act = new Act(id, putDateTime(dateString), email, idLocal,
						idGuest);
				actsList.add(act);
			} while (c.moveToNext());
		}

		return actsList;
	}

	// ------------------------ "teams" table methods ----------------//
	/**
	 * Insert a Team in the DB
	 * 
	 * @param team
	 * @return the id of the inserted team
	 */
	public long insertTeam(Team team) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TAG_TEAMNAME, team.getName());
		values.put(TAG_LEAGUEID, team.getLeagueId());

		// insert row
		long team_id = db.insertWithOnConflict(TAG_TEAMS, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);

		// return the id of the inserted team
		return team_id;
	}

	/**
	 * Pull a team of with the given ID
	 * 
	 * @param team_id
	 * @return the requested team
	 */
	public Team selectTeam(int team_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TAG_TEAMS + " WHERE "
				+ TAG_TID + " = " + team_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		int id = c.getInt(c.getColumnIndex(TAG_TID));
		String name = c.getString(c.getColumnIndex(TAG_TEAMNAME));
		int idLeague = c.getInt(c.getColumnIndex(TAG_LEAGUEID));

		return new Team(id, name, idLeague);
	}

	// ------------------------ "players" table methods ----------------//
	/**
	 * Insert Players in the DB, given a list of them
	 * 
	 * @param players
	 */
	public void insertPlayers(ArrayList<Player> players) {
		for (Player player : players) {
			insertPlayer(player);
		}
	}

	/**
	 * Insert a single player
	 * 
	 * @param player
	 */
	private void insertPlayer(Player player) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TAG_LICENSE, player.getLicenseNumber());
		values.put(TAG_PLAYERNAME, player.getName());
		values.put(TAG_SURNAME, player.getSurname());
		values.put(TAG_TEAMSID, player.getTeamId());

		// insert row
		db.insertWithOnConflict(TAG_PLAYERS, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
	}

	/**
	 * Select all player given a specific team.
	 * 
	 * @param teams_id
	 * @return a list of
	 */
	public ArrayList<Player> selectPlayers(int teams_id) {
		ArrayList<Player> playersList = new ArrayList<Player>();
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TAG_PLAYERS + " WHERE "
				+ TAG_TEAMSID + " = " + teams_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				int licensnumber = c.getInt(c.getColumnIndex(TAG_LICENSE));
				String name = c.getString(c.getColumnIndex(TAG_PLAYERNAME));
				String surname = c.getString(c.getColumnIndex(TAG_SURNAME));
				int idTeam = c.getInt(c.getColumnIndex(TAG_TEAMSID));

				Player player = new Player(licensnumber, name, surname, idTeam);

				playersList.add(player);
			} while (c.moveToNext());
		}

		return playersList;
	}

	/**
	 * Delete a Player from the DB
	 * 
	 * @param licensnumber
	 */
	public void deletePlayer(int licensnumber) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TAG_PLAYERS, TAG_LICENSE + " = ?",
				new String[] { String.valueOf(licensnumber) });
	}

	// ------------------------ "events" table methods ----------------//
	/**
	 * Insert a given list of Events
	 * 
	 * @param players
	 */
	public void insertEvents(ArrayList<Event> players) {
		for (Event player : players) {
			insertEvent(player);
		}
	}

	/**
	 * Insert a single Event
	 * 
	 * @param event
	 * @return The ID of the inserted Event.
	 */
	public long insertEvent(Event event) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TAG_ACTSID, event.getActId());
		values.put(TAG_MINUTE, event.getMinute());
		values.put(TAG_TYPE, event.getType());
		values.put(TAG_VALUE, event.getValue());
		values.put(TAG_PLNUMBER, event.getPlayer());

		// insert row
		return db.insert(TAG_EVENTS, null, values);
	}

	/**
	 * Get all the events attached to an Act.
	 * 
	 * @param act_id
	 * @return List of Events that has an Act.
	 */
	public ArrayList<Event> selectEvents(int act_id) {

		ArrayList<Event> eventsList = new ArrayList<Event>();
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TAG_EVENTS + " WHERE "
				+ TAG_ACTSID + " = " + act_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				int id = c.getInt(c.getColumnIndex(TAG_EID));
				int idAct = c.getInt(c.getColumnIndex(TAG_ACTSID));
				int minute = c.getInt(c.getColumnIndex(TAG_MINUTE));
				String type = c.getString(c.getColumnIndex(TAG_TYPE));
				String value = c.getString(c.getColumnIndex(TAG_VALUE));
				int playersLicensnumber = c.getInt(c
						.getColumnIndex(TAG_PLNUMBER));

				Event event = new Event(id, idAct, minute, type, value,
						playersLicensnumber);
				eventsList.add(event);
			} while (c.moveToNext());
		}

		return eventsList;
	}

	// --------------- other methods ----------------//

	/**
	 * Deleting DB
	 */
	public void clearDB() {

		SQLiteDatabase db = this.getReadableDatabase();
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TAG_ACTS);
		db.execSQL("DROP TABLE IF EXISTS " + TAG_TEAMS);
		db.execSQL("DROP TABLE IF EXISTS " + TAG_PLAYERS);
		db.execSQL("DROP TABLE IF EXISTS " + TAG_EVENTS);

		// create new tables
		onCreate(db);
	}

	/**
	 * closing database
	 */
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	/**
	 * Convert Date to String
	 * 
	 * @param date
	 * @return Date as a String
	 */
	private String getDateTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		return dateFormat.format(date);
	}

	/**
	 * Convert String to Date
	 * 
	 * @param dateString
	 * @return Date
	 */
	private Date putDateTime(String dateString) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}
}
