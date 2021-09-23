import 'package:flutter_helper/boss/utils.dart';
import 'package:flutter_helper/samples/flutter_neumorphic/lib/flutter_neumorphic.dart';
import 'package:flutter_helper/templates/profileapp/utils.dart';
import 'dart:math';

//https://github.com/tomialagbe/flutter_ui_challenges#readme
class ProfilePage extends StatefulWidget {
  @override
  _ProfilePageState createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  final Profile profile = getProfile();

  @override
  Widget build(BuildContext context) {
    return ListView(
      children: [
        _profileHeader(),
        _quickActions(),
        _buildItem("Memories", Icons.camera),
        _buildItem("Favourites", Icons.favorite),
        _buildItem("Presents", Icons.card_giftcard),
        _buildItem("Friends", Icons.people),
        _buildItem("Achievement", Icons.home),
      ],
    );
  }

  _profileHeader() {
    final topPadding = MediaQuery.of(context).padding.top;
    final headerGradient = new RadialGradient(
      center: Alignment.topLeft,
      radius: 0.4,
      colors: <Color>[
        const Color(0xFF8860EB),
        const Color(0xFF8881EB),
      ],
      stops: <double>[
        0.4,
        1.0,
      ],
      tileMode: TileMode.repeated,
    );
    final headerHeight = 290.0;

    return Container(
      height: headerHeight,
      decoration: new BoxDecoration(
        color: PColors.primaryColor,
        boxShadow: <BoxShadow>[
          new BoxShadow(spreadRadius: 2.0,
              blurRadius: 4.0,
              offset: new Offset(0.0, 1.0),
              color: Colors.black38),
        ],
      ),
      child: new Stack(
        fit: StackFit.expand,
        children: <Widget>[
          // linear gradient
          new Container(
            height: headerHeight,
            decoration: new BoxDecoration(
              gradient: new LinearGradient(
                  colors: <Color>[ //7928D1
                    const Color(0xFF7928D1), const Color(0xFF9A4DFF)],
                  stops: <double>[0.3, 0.5],
                  begin: Alignment.topRight, end: Alignment.bottomLeft
              ),
            ),
          ),
          // radial gradient
          new CustomPaint(
            painter: new HeaderGradientPainter(),
          ),
          new Padding(
            padding: new EdgeInsets.only(
                top: topPadding, left: 15.0, right: 15.0, bottom: 20.0),
            child: new Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                _buildBellIcon(),
                new Padding(
                  padding: const EdgeInsets.only(bottom: 15.0),
                  child: _buildTitle(),
                ),
                new Padding(
                  padding: const EdgeInsets.only(bottom: 20.0),
                  child: _buildAvatar(),
                ),
                _buildFollowerStats()
              ],
            ),
          ),
        ],
      ),
    );
  }
  /// Build the bell icon at the top right corner of the header
  Widget _buildBellIcon() {
    return new Row(
      mainAxisAlignment: MainAxisAlignment.end,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        new IconButton(
            icon: new Icon(
              LAIcons.bell, color: Colors.white, size: 30.0,),
            onPressed: () {}),
      ],
    );
  }
  Widget _buildTitle() {
    return new Text("Profile",
        style: new TextStyle(
            fontFamily: ProfileFontNames.TimeBurner,
            fontWeight: FontWeight.w700,
            color: Colors.white,
            fontSize: 40.0,
            letterSpacing: 1.0));
  }

  /// The avatar consists of the profile image, the users name and location
  Widget _buildAvatar() {
    final mainTextStyle = new TextStyle(fontFamily: ProfileFontNames.TimeBurner,
        color: Colors.white,
        fontWeight: FontWeight.w700,
        fontSize: 20.0);
    final subTextStyle = new TextStyle(
        fontFamily: ProfileFontNames.TimeBurner,
        fontSize: 16.0,
        color: Colors.white70,
        fontWeight: FontWeight.w700);

    return new Row(
      children: <Widget>[
        new Container(
          width: 70.0, height: 60.0,
          decoration: new BoxDecoration(
            image: new DecorationImage(
                image: new AssetImage("assets/images/emma-watson.jpg"),
                fit: BoxFit.cover),
            borderRadius: new BorderRadius.all(new Radius.circular(20.0)),
            boxShadow: <BoxShadow>[
              new BoxShadow(
                  color: Colors.black26, blurRadius: 5.0, spreadRadius: 1.0),
            ],
          ),
        ),
        new Padding(padding: const EdgeInsets.only(right: 20.0)),
        new Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            new Text(profile.fullName, style: mainTextStyle),
            new Text(profile.location, style: subTextStyle),
          ],
        ),
      ],
    );
  }

  Widget _buildFollowerStats() {
    return new Row(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: <Widget>[
        _buildFollowerStat("Followers", profile.numberOfFollowersString),
        _buildVerticalDivider(),
        _buildFollowerStat("Following", profile.numberFollowingString),
        _buildVerticalDivider(),
        _buildFollowerStat("Total Likes", profile.totalLikesString),
      ],
    );
  }

  Widget _buildFollowerStat(String title, String value) {
    final titleStyle = new TextStyle(
        fontSize: 16.0,
        fontFamily: ProfileFontNames.TimeBurner,
        color: Colors.white);
    final valueStyle = new TextStyle(
        fontFamily: ProfileFontNames.TimeBurner,
        fontSize: 18.0,
        fontWeight: FontWeight.w700,
        color: Colors.white);
    return new Column(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: <Widget>[
        new Text(title, style: titleStyle),
        new Text(value, style: valueStyle),
      ],
    );
  }

  Widget _buildVerticalDivider() {
    return new Container(
      height: 30.0,
      width: 1.0,
      color: Colors.white30,
      margin: const EdgeInsets.only(left: 10.0, right: 10.0),
    );
  }

  // var microphone = AssetImage("assets/images/microphone.png");
  // var wallet =  AssetImage("assets/images/wallet.png");
  // var joystick =  AssetImage("assets/images/joystick.png");

  _buildAction(String title, Color c, Gradient gra, ImageProvider img) {
    final textStyle = TextStyle(
        color: Colors.white,
        fontWeight: FontWeight.w700,
        fontSize: 18.0,
        fontFamily: ProfileFontNames.TimeBurner);
    return GestureDetector(
      onTap: () {
        showToast(title);
      },
      child: Container(
        margin: EdgeInsets.only(right: 5.0, left: 5.0),
        width: 150.0,
        decoration: BoxDecoration(
          color: c,
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(10.0),
          gradient: gra,
          boxShadow: [
            BoxShadow(
              blurRadius: 2.0,
              spreadRadius: 1.0,
              offset: Offset(0.0, 1.0),
            )
          ],
        ),
        child: Stack(
          children: [
            Opacity(
              opacity: 0.2,
              child: Align(
                alignment: Alignment.centerRight,
                child: Transform.rotate(
                  angle: -pi / 4.8,
                  alignment: Alignment.centerRight,
                  child: ClipPath(
                    clipper: _BackgroundImageClipper(),
                    child: Container(
                      padding: EdgeInsets.only(
                        bottom: 20.0,
                        left: 60.0,
                      ),
                      child: Image(
                        width: 90.0,
                        height: 90.0,
                        image: img,
                      ),
                    ),
                  ),
                ),
              ),
            ),
            Container(
              alignment: Alignment.topLeft,
              padding: EdgeInsets.only(left: 10.0, top: 10.0),
              child: Text(title, style: textStyle),
            ),
          ],
        ),
      ),
    );
  }

  _quickActions() {
    final blueGradient = LinearGradient(
        colors: <Color>[Color(0xFF0075D1), Color(0xFF00A2E3)],
        stops: <double>[0.4, 0.6],
        begin: Alignment.topRight,
        end: Alignment.bottomLeft);
    final purpleGraient = LinearGradient(
        colors: <Color>[Color(0xFF882DEB), Color(0xFF9A4DFF)],
        stops: <double>[0.5, 0.7],
        begin: Alignment.topLeft,
        end: Alignment.bottomRight);
    final redGradient = LinearGradient(
        colors: <Color>[Color(0xFFBA110E), Color(0xFFCF3110)],
        stops: <double>[0.6, 0.8],
        begin: Alignment.bottomRight,
        end: Alignment.topLeft);

    return Container(
      constraints: BoxConstraints(maxHeight: 120.0),
      margin: EdgeInsets.only(top: 20.0),
      child: ListView(
        padding:
            EdgeInsets.only(left: 10.0, bottom: 20.0, right: 10.0, top: 10.0),
        shrinkWrap: true,
        scrollDirection: Axis.horizontal,
        children: [
          _buildAction(
            "Live\nBroadcast",
            Colors.blue,
            blueGradient,
            new AssetImage("assets/images/microphone.png"),
          ),
          _buildAction(
            "My\nWallet",
            Colors.purple,
            purpleGraient,
            new AssetImage("assets/images/wallet.png"),
          ),
          _buildAction(
            "Game\nCenter",
            Colors.red,
            redGradient,
            new AssetImage("assets/images/joystick.png"),
          ),
        ],
      ),
    );
  }

  _buildItem(String title, IconData iconData) {
    final textStyle = TextStyle(
      color: Colors.black54,
      fontSize: 18.0,
      fontWeight: FontWeight.w600,
    );
    return InkWell(
      onTap: () {
        showToast(title);
      },
      child: Padding(
        padding: EdgeInsets.only(left: 16.0, right: 16.0),
        child: Row(
          children: [
            Container(
              decoration: BoxDecoration(
                color: Colors.purple,
                borderRadius: BorderRadius.circular(5.0),
              ),
              width: 35.0,
              height: 35.0,
              child: Icon(iconData, color: Colors.white, size: 24.0),
            ),
            Padding(
              padding: EdgeInsets.only(left: 10.0, right: 10.0),
              child: Text(title, style: textStyle),
            ),
            Expanded(child: Container()),
            IconButton(
              onPressed: () {},
              icon: Icon(Icons.chevron_right, color: Colors.black26),
            ),
          ],
        ),
      ),
    );
  }
}

class _BackgroundImageClipper extends CustomClipper<Path> {
  @override
  Path getClip(Size size) {
    final path = Path();
    path.moveTo(0.0, 0.0);
    path.lineTo(size.width, size.height);
    path.lineTo(size.width, 0.0);
    path.close();
    return path;
  }

  @override
  bool shouldReclip(covariant CustomClipper<Path> oldClipper) => false;
}


class HeaderGradientPainter extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    // TODO: paint background radial gradient
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => false;

}