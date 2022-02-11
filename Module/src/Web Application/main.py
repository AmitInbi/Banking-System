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
        # initialize active_session api from JavaGateway ()
        gateway = JavaGateway()
        api = gateway.entry_point.getAPI()
        # loads active_session as a global variable and inserts api object
        global active_session
        active_session = api

        session["user_id"] = active_session.valPass(username, password)

        if session["user_id"] != 1:
            active_session.createConnection(session.get("user_id"))
            global LoggedIn
            LoggedIn = True
            return redirect(url_for("homePage"))
        else:
            return redirect(url_for("LoginPage"))

        # Temporary: get random user by id
        # from random import randrange
        # session["user_id"] = randrange(3)
        # active_session.createConnection(session.get("user_id"))

    else:
        return render_template("login_page.html")


@app.route("/deposit", methods=["GET", "POST"])
def Deposit():
    if request.method == "POST":
        active_session.customerDeposit(int(request.form['Deposit']))
        return redirect(url_for("Deposit"))
    else:
        return render_template("Deposit.html", balance=active_session.getCustomerBalance())


@app.route("/withdraw", methods=["GET", "POST"])
def Withdraw():
    if request.method == "POST":
        active_session.customerWithdrawal(int(request.form['Withdraw']))
        return redirect(url_for("Withdraw"))
    else:
        return render_template("Withdrawal.html", balance=active_session.getCustomerBalance())


@app.route("/Operations_Log", methods=["GET", "POST"])
def opLog():
    return render_template("opLog.html", opLog=list(active_session.getCustomerOpLog()))


if __name__ == '__main__':
    print("Initializing app")
    app.run(debug=True)
