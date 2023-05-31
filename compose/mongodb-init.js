const dbName = process.env.MONGO_INITDB_DATABASE
const dbUser = process.env.MONGO_USERNAME
const dbPass = process.env.MONGO_PASSWORD

if (dbName === undefined ||
    dbUser === undefined ||
    dbPass === undefined
) {
    print('Missing environment variables.')
    exit(1)
}

db.createUser({
    user: dbUser,
    pwd: dbPass,
    roles: [
        {
            role: "readWrite",
            db: dbName
        }
    ]
})
