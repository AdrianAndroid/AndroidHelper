(function(t) {
	function e(e) {
		for (var o, u, c = e[0], a = e[1], s = e[2], d = 0, l = []; d < c.length; d++) u = c[d],
			Object.prototype.hasOwnProperty.call(r, u) && r[u] && l.push(r[u][0]),
			r[u] = 0;
		for (o in a) Object.prototype.hasOwnProperty.call(a, o) && (t[o] = a[o]);
		f && f(e);
		while (l.length) l.shift()();
		return i.push.apply(i, s || []),
			n()
	}

	function n() {
		for (var t, e = 0; e < i.length; e++) {
			for (var n = i[e], o = !0, c = 1; c < n.length; c++) {
				var a = n[c];
				0 !== r[a] && (o = !1)
			}
			o && (i.splice(e--, 1), t = u(u.s = n[0]))
		}
		return t
	}
	var o = {},
		r = {
			des: 0
		},
		i = [];

	function u(e) {
		if (o[e]) return o[e].exports;
		var n = o[e] = {
			i: e,
			l: !1,
			exports: {}
		};
		return t[e].call(n.exports, n, n.exports, u),
			n.l = !0,
			n.exports
	}
	u.m = t,
		u.c = o,
		u.d = function(t, e, n) {
			u.o(t, e) || Object.defineProperty(t, e, {
				enumerable: !0,
				get: n
			})
		},
		u.r = function(t) {
			"undefined" !== typeof Symbol && Symbol.toStringTag && Object.defineProperty(t, Symbol.toStringTag, {
					value: "Module"
				}),
				Object.defineProperty(t, "__esModule", {
					value: !0
				})
		},
		u.t = function(t, e) {
			if (1 & e && (t = u(t)), 8 & e) return t;
			if (4 & e && "object" === typeof t && t && t.__esModule) return t;
			var n = Object.create(null);
			if (u.r(n), Object.defineProperty(n, "default", {
					enumerable: !0,
					value: t
				}), 2 & e && "string" != typeof t)
				for (var o in t) u.d(n, o,
					function(e) {
						return t[e]
					}.bind(null, o));
			return n
		},
		u.n = function(t) {
			var e = t && t.__esModule ?
				function() {
					return t["default"]
				} : function() {
					return t
				};
			return u.d(e, "a", e),
				e
		},
		u.o = function(t, e) {
			return Object.prototype.hasOwnProperty.call(t, e)
		},
		u.p = "";
	var c = window["webpackJsonp"] = window["webpackJsonp"] || [],
		a = c.push.bind(c);
	c.push = e,
		c = c.slice();
	for (var s = 0; s < c.length; s++) e(c[s]);
	var f = a;
	i.push([0, "chunk-vendors"]),
		n()
})({
	0: function(t, e, n) {
		t.exports = n("f122")
	},
	"0f4f": function(t, e, n) {},
	"152b": function(t, e, n) {
		"use strict";
		var o = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("div", {
					ref: "renderContent",
					staticClass: "test-conetnt-detail",
					class: t.className,
					attrs: {
						id: "testContent"
					},
					domProps: {
						innerHTML: t._s(t.convertImgStringToImg(t.testCotent))
					}
				})
			},
			r = [],
			i = (n("ac1f"), n("5319"), n("99af"), !1),
			u = function() {
				window.MathJax && (window.MathJax.Hub.Config({
					showProcessingMessages: !1,
					messageStyle: "none",
					jax: ["input/TeX", "output/HTML-CSS"],
					tex2jax: {
						inlineMath: [
							["$", "$"],
							["\\(", "\\)"]
						],
						displayMath: [
							["$$", "$$"],
							["\\[", "\\]"]
						],
						skipTags: ["script", "noscript", "style", "textarea", "pre", "code", "a"]
					},
					"HTML-CSS": {
						availableFonts: ["STIX", "TeX"],
						showMathMenu: !1
					}
				}), i = !0)
			},
			c = function() {
				window.MathJax && window.MathJax.Hub.Queue(["Typeset", window.MathJax.Hub, document
					.getElementById("app")
				])
			},
			a = n("fc0d"),
			s = {
				props: {
					testCotent: {
						type: String,
						default: ""
					},
					className: {
						type: String,
						default: ""
					}
				},
				mounted: function() {
					document.getElementById("testContent").addEventListener("click", (function(t) {
						if ("IMG" === t.target.nodeName) try {
							a["a"].showImg(t.target.src)
						} catch (e) {
							console.log("showImgError：", e)
						}
					}))
				},
				name: "TestContent",
				methods: {
					initMathConfig: function() {
						i || u(),
							this.$nextTick((function() {
								c()
							}))
					},
					convertImgStringToImg: function(t) {
						return "" === t ? "" : (t = t.replace(/↵/g, "<br>"), t.replace(/\\(\(|\)|\[|\])/g,
							"$$$$").replace(
							/\[(center|left|right)\]\[img\](.*?)\[\/img\]\[\/(center|left|right)\]/g,
							(function(t, e, n) {
								var o = "";
								return "left" === e ? o = "left" : "right" === e && (o =
										"right"),
									'<div class="img-box '.concat(o, '"><img src=').concat(n,
										"></div>")
							})))
					}
				},
				watch: {
					testCotent: {
						immediate: !0,
						handler: function(t) {
							var e = this;
							t && this.$nextTick().then((function() {
								e.initMathConfig()
							}))
						}
					}
				}
			},
			f = s,
			d = (n("68b2"), n("2877")),
			l = Object(d["a"])(f, o, r, !1, null, null, null);
		e["a"] = l.exports
	},
	"1bc6": function(t, e, n) {
		"use strict";
		n("b0d0");
		var o = n("3c69"),
			r = (n("66b9"), n("b650")),
			i = (n("a44c"), n("e27c")),
			u = (n("4ddd"), n("9f14")),
			c = n("2b0e"),
			a = n("91f4"),
			s = n.n(a);
		n("157a");
		c["default"].config.productionTip = !1,
			c["default"].use(u["a"]),
			c["default"].use(i["a"]),
			c["default"].use(r["a"]),
			o["a"].use("en-US", s.a),
			e["a"] = c["default"]
	},
	"47d5": function(t, e, n) {
		"use strict";
		n("8f02")
	},
	"68b2": function(t, e, n) {
		"use strict";
		n("0f4f")
	},
	"8f02": function(t, e, n) {},
	f122: function(t, e, n) {
		"use strict";
		n.r(e);
		n("e260"),
			n("e6cf"),
			n("cca6"),
			n("a79d");
		var o = n("1bc6"),
			r = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("div", {
						staticClass: "page"
					},
					[n("testContent", {
						staticClass: "no-top",
						attrs: {
							"test-cotent": t.des
						}
					})], 1)
			},
			i = [],
			u = n("152b"),
			c = {
				data: function() {
					return {
						des: ""
					}
				},
				created: function() {
					var t = this;
					window.showDes = function(e) {
						t.des = e.value
					}
				},
				components: {
					testContent: u["a"]
				},
				methods: {
					goPage: function() {
						location.href = "/about"
					}
				}
			},
			a = c,
			s = (n("47d5"), n("2877")),
			f = Object(s["a"])(a, r, i, !1, null, "2bff652a", null),
			d = f.exports;
		o["a"].config.productionTip = !1,
			new o["a"]({
				render: function(t) {
					return t(d)
				}
			}).$mount("#app")
	},
	fc0d: function(t, e, n) {
		"use strict";
		n("d3b7"),
			n("a9e3"),
			n("4add");
		var o = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("data", "webToken", {},
							(function(e) {
								return t(e)
							}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			r = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("data", "uid", {},
							(function(e) {
								return t(e)
							}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			i = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("data", "userId", {},
							(function(e) {
								return t(e)
							}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			u = function(t) {
				return new Promise((function(e, n) {
					try {
						window.YYApiCore.invokeClientMethod("data", "showImg", {
								url: t
							},
							(function(t) {
								return e(t)
							}))
					} catch (o) {
						return n(o)
					}
				}))
			},
			c = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("data", "instituteId", {},
							(function(e) {
								return t(e)
							}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			a = function(t) {
				return new Promise((function(e) {
					try {
						window.YYApiCore.invokeClientMethod("ui", "setNavigationBar", {
								title: {
									title: t.title
								},
								leftItem: {
									id: t.id,
									enabled: t.enabled || "false"
								}
							},
							(function(n) {
								"true" === t.enabled && Number(n.id) === Number(t.id) && t
									.bc(),
									e(n)
							}))
					} catch (n) {
						return e(0)
					}
				}))
			},
			s = function() {
				try {
					window.YYApiCore.invokeClientMethod("ui", "popViewController")
				} catch (t) {
					console.log(t)
				}
			},
			f = function(t) {
				return new Promise((function(e, n) {
					try {
						window.YYApiCore.invokeClientMethod("data", "addPicture", {
								pictureNum: t
							},
							(function(t) {
								t && t.picture && e(t)
							}))
					} catch (o) {
						return n(o)
					}
				}))
			},
			d = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("data", "closeAppLoading", (function(
						e) {
							t(e)
						}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			l = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("data", "deviceInfo", {},
							(function(e) {
								t(e)
							}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			p = function(t) {
				return new Promise((function(e, n) {
					try {
						window.YYApiCore.invokeClientMethod("data", "isSupport", {
								functionName: t
							},
							(function(t) {
								e(t)
							}))
					} catch (o) {
						return n(o)
					}
				}))
			},
			h = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("ui", "popViewController", {},
							(function(e) {
								t(e)
							}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			w = function(t) {
				return new Promise((function(e, n) {
					try {
						window.YYApiCore.invokeClientMethod("data", "playVideo", {
								url: t
							},
							(function(t) {
								e(t)
							}))
					} catch (o) {
						return n(o)
					}
				}))
			};
		e["a"] = {
			getWebToken: o,
			getUid: r,
			getUserId: i,
			getSetesId: c,
			backListener: a,
			giveupH5: s,
			appAddPicture: f,
			closeAppLoading: d,
			getAppDeviceInfo: l,
			getAppIsSupMethods: p,
			setPopViewController: h,
			setNativeVideo: w,
			showImg: u
		}
	}
});
