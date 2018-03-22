import * as mysql from 'mysql';

export default class Database {
	private pool;

	constructor(config) {
		console.log("Creating DB pool", config.host, config.user);
		this.pool = mysql.createPool({
			host: config.host,
			user: config.user,
			password: config.password,
			database: config.database,
			insecureAuth : true
		});		
	}

	openConnection() {
		return new Promise((resolve, reject)=> {
			this.pool.getConnection((err, connection)=>{
				if (err) {
					reject(err);
				} else {
					resolve(connection);
				}
			});
		})
		.catch((error)=>{console.log("ERROR during connection", error)});
	}

	query(connection, sql, args) {
		return new Promise((resolve, reject)=>{
			connection.query(sql, args, (err, result)=>{
				if (err) {
					reject(err);
				} else {
					resolve(result);
				}
				this.pool.releaseConnection(connection);
			});
		})
		.catch((error)=>{console.log("ERROR during query", error)});
	}	
}