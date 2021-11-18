from flask import Flask, render_template, request, redirect, url_for, session
from os import urandom
# import json

from py4j.java_gateway import JavaGateway

app = Flask(__name__)
app.config['SECRET_KEY'] = urandom(16).hex()
app.config['SEND_FILE_MAX_AGE_DEFAULT'] = 0  # stop app caching

# Loads the JAVA API class for managing the connection to the JAVA server back-end
active_session = None

# Track users log in status
LoggedIn = False


@app.route("/", methods=["GET", "POST"])
def homePage():
    global LoggedIn
    if not LoggedIn:
        return redirect(url_for("LoginPage"))
    else:
        return render_template("home.html", user_name=active_session.getCustomerName())


@app.route("/Login_page", methods=["GET", "POST"])
def LoginPage():
    if request.method == "POST":
        username = request.form['username']
        password = request.form['password']

        # TODO: verify username and password by checking hash values in database
        # TODO: when verifying return the id number of the user
        global LoggedIn
        LoggedIn = True
        # Temporary: get random user by id
        from random import randrange
        session["user_id"] = randrange(3)

        # initialize active_session api from JavaGateway ()
        gateway = JavaGateway()
        api = gateway.entry_point.getAPI()
        # loads active_session as a global variable and inserts api object
        global active_session
        active_session = api
        active_session.createConnection(session.get("user_id"))

        return redirect(url_for("homePage"))
    else:
        return render_template("login_page.html")


@app.route("/deposit", methods=["GET", "POST"])
def Deposit():
    if request.method == "POST":
        active_session.customerDeposit(int(request.form['Deposit']))
        return redirect(url_for("Deposit"))
    else:
        return render_template("Deposit.html", balance=int(active_session.getCustomerBalance()))


if __name__ == '__main__':
    print("Initializing app")
    app.run(debug=True)
