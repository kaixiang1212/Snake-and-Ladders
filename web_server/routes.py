from flask import render_template, request, redirect, url_for, abort, flash
from flask_login import current_user, login_required, login_user, logout_user
from server import *

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # Create a socket object


@app.route('/game', methods=['GET', 'POST'])
@login_required
def game():
    if not current_user.is_ready():
        return redirect(url_for('player_selection'))
    if request.method == 'POST':
        player = current_user
        try :
            if "roll" in request.form:
                player.roll()
            elif "stop" in request.form:
                player.stop()
        except ConnectionError:
            return redirect(url_for('server_error'))

    return render_template('index.html')


@app.route('/player_selection', methods=['GET', 'POST'])
@login_required
def player_selection():
    if current_user.is_ready():
        return redirect(url_for('game'))
    try:
        current_user.send("clear\n")
    except ConnectionError:
        return redirect(url_for('maximum_player'))

    if request.method == 'POST':
        try:
            if "next" in request.form:
                current_user.next()
            elif "change" in request.form:
                new_name = request.form['new_name']
                current_user.change(new_name)
            elif "ready" in request.form:
                current_user.ready()
                return redirect(url_for('game'))
        except ConnectionError:
            return redirect(url_for('server_error'))

    return render_template('player_selection.html')


@app.route('/login', methods=['GET', 'POST'])
def login():
    if current_user in players:
        redirect(url_for('index'))
    if request.method == 'POST':
        try:
            user = add_player(request.form['player_name'])
        except RuntimeError:
            return redirect(url_for('server_error'))

        login_user(user)
        player = user
        player.change(request.form['player_name'])
        return redirect(url_for('player_selection'))

    return render_template('login.html')


@app.route('/')
def index():
    if current_user not in players:
        return redirect(url_for('login'))
    elif current_user.is_ready():
        redirect(url_for('game'))
    return redirect(url_for('player_selection'))


@app.route('/error')
def server_error():
    return render_template("server_error.html")


@app.route('/maximum_player')
def maximum_player():
    return render_template("maximum_player.html")