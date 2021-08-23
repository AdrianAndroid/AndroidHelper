(function(t) {
	function e(e) {
		for (var s, o, r = e[0], c = e[1], u = e[2], d = 0, m = []; d < r.length; d++) o = r[d], Object.prototype
			.hasOwnProperty.call(a, o) && a[o] && m.push(a[o][0]), a[o] = 0;
		for (s in c) Object.prototype.hasOwnProperty.call(c, s) && (t[s] = c[s]);
		l && l(e);
		while (m.length) m.shift()();
		return i.push.apply(i, u || []), n()
	}

	function n() {
		for (var t, e = 0; e < i.length; e++) {
			for (var n = i[e], s = !0, r = 1; r < n.length; r++) {
				var c = n[r];
				0 !== a[c] && (s = !1)
			}
			s && (i.splice(e--, 1), t = o(o.s = n[0]))
		}
		return t
	}
	var s = {},
		a = {
			index: 0
		},
		i = [];

	function o(e) {
		if (s[e]) return s[e].exports;
		var n = s[e] = {
			i: e,
			l: !1,
			exports: {}
		};
		return t[e].call(n.exports, n, n.exports, o), n.l = !0, n.exports
	}
	o.m = t, o.c = s, o.d = function(t, e, n) {
		o.o(t, e) || Object.defineProperty(t, e, {
			enumerable: !0,
			get: n
		})
	}, o.r = function(t) {
		"undefined" !== typeof Symbol && Symbol.toStringTag && Object.defineProperty(t, Symbol.toStringTag, {
			value: "Module"
		}), Object.defineProperty(t, "__esModule", {
			value: !0
		})
	}, o.t = function(t, e) {
		if (1 & e && (t = o(t)), 8 & e) return t;
		if (4 & e && "object" === typeof t && t && t.__esModule) return t;
		var n = Object.create(null);
		if (o.r(n), Object.defineProperty(n, "default", {
				enumerable: !0,
				value: t
			}), 2 & e && "string" != typeof t)
			for (var s in t) o.d(n, s, function(e) {
				return t[e]
			}.bind(null, s));
		return n
	}, o.n = function(t) {
		var e = t && t.__esModule ? function() {
			return t["default"]
		} : function() {
			return t
		};
		return o.d(e, "a", e), e
	}, o.o = function(t, e) {
		return Object.prototype.hasOwnProperty.call(t, e)
	}, o.p = "";
	var r = window["webpackJsonp"] = window["webpackJsonp"] || [],
		c = r.push.bind(r);
	r.push = e, r = r.slice();
	for (var u = 0; u < r.length; u++) e(r[u]);
	var l = c;
	i.push([1, "chunk-vendors"]), n()
})({
	"0032": function(t, e) {
		t.exports =
			"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAcCAYAAAByDd+UAAAACXBIWXMAABYlAAAWJQFJUiTwAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAGUSURBVHgB5ZY/S8NAGId/77Wog9Y6CIIdqlt10W4KDt0cBQdBFxHcRKiL+AEcFKHfQCcdBBE3cbC46ZTJdukQ0EFUaCkdlDY97w20aGzSNkkD4gMH9y88ubv33oSgiGcz8ZA0TiQwo5pR+AxJXBoitKOn0jqxTEhD64XIQrFOoaSgupEJQMaMkDSOBRGWEBCkjkwgWKJBC/H3hYnBUYwPRGzHw/CJSLgfp7MrmFJCdZ+xpp3jofT0a54vK/wuY1Q0Yj2WbDnXs9Aqa1CuffovtJPlKq/YL2S7Ey6PTUNb2MLd3KZZtzLkIOPzs1uhbdBsT8ybK+BymFg0+y5eHpuyMxcyR+FwuO9HuyG9eS+4ljE0eXskWw3wNh4oCVn6nz/KiFnuWacyhbQ9Q96+3fw1rG/jQWbiGKV2UreytkInad6FrCOhVcrlXqWsVRcypuNcylLOjXxNcpU3uKWr5M0R6pX/8QEuIUCEijoNASEhr4Sk6oaqF9F7ipJqaaGn9vQ6VZNS/Y4DzavWrnRDST2QZQe7vgBe58ZuEGH8kQAAAABJRU5ErkJggg=="
	},
	"0b8a": function(t, e, n) {},
	"0e8c": function(t, e, n) {
		"use strict";
		n("58a8")
	},
	"0f4f": function(t, e, n) {},
	1: function(t, e, n) {
		t.exports = n("44eb")
	},
	"10f5": function(t, e, n) {},
	"152b": function(t, e, n) {
		"use strict";
		var s = function() {
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
			a = [],
			i = (n("ac1f"), n("5319"), n("99af"), !1),
			o = function() {
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
			r = function() {
				window.MathJax && window.MathJax.Hub.Queue(["Typeset", window.MathJax.Hub, document
					.getElementById("app")
				])
			},
			c = n("fc0d"),
			u = {
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
							c["a"].showImg(t.target.src)
						} catch (e) {
							console.log("showImgError：", e)
						}
					}))
				},
				name: "TestContent",
				methods: {
					initMathConfig: function() {
						i || o(), this.$nextTick((function() {
							r()
						}))
					},
					convertImgStringToImg: function(t) {
						return "" === t ? "" : (t = t.replace(/↵/g, "<br>"), t.replace(/\\(\(|\)|\[|\])/g,
							"$$$$").replace(
							/\[(center|left|right)\]\[img\](.*?)\[\/img\]\[\/(center|left|right)\]/g,
							(function(t, e, n) {
								var s = "";
								return "left" === e ? s = "left" : "right" === e && (s =
									"right"), '<div class="img-box '.concat(s,
									'"><img src=').concat(n, "></div>")
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
			l = u,
			d = (n("68b2"), n("2877")),
			m = Object(d["a"])(l, s, a, !1, null, null, null);
		e["a"] = m.exports
	},
	1691: function(t, e, n) {},
	"1bc6": function(t, e, n) {
		"use strict";
		n("b0d0");
		var s = n("3c69"),
			a = (n("66b9"), n("b650")),
			i = (n("a44c"), n("e27c")),
			o = (n("4ddd"), n("9f14")),
			r = n("2b0e"),
			c = n("91f4"),
			u = n.n(c);
		n("157a");
		r["default"].config.productionTip = !1, r["default"].use(o["a"]), r["default"].use(i["a"]), r["default"]
			.use(a["a"]), s["a"].use("en-US", u.a), e["a"] = r["default"]
	},
	"1faf": function(t, e, n) {},
	2491: function(t, e, n) {},
	"24ca": function(t, e, n) {},
	"2e33": function(t, e, n) {
		"use strict";
		n("10f5")
	},
	"3e06": function(t, e, n) {
		"use strict";
		n("815f")
	},
	4272: function(t, e, n) {
		"use strict";
		n("5c97")
	},
	"44eb": function(t, e, n) {
		"use strict";
		n.r(e);
		n("b0d0");
		var s = n("3c69"),
			a = (n("c3a6"), n("ad06")),
			i = (n("c194"), n("7744")),
			o = (n("6d73"), n("473d")),
			r = (n("91d5"), n("f0ca")),
			c = (n("2cbd"), n("ab2c")),
			u = (n("2994"), n("2bdd")),
			l = (n("f1dc"), n("6e47")),
			d = (n("e566"), n("5d26")),
			m = (n("be7f"), n("565f")),
			f = (n("ac1e"), n("543e")),
			p = (n("e7e5"), n("d399")),
			h = (n("a52c"), n("2ed4")),
			g = (n("537a"), n("ac28")),
			v = (n("4056"), n("44bf")),
			b = (n("8a58"), n("e41f")),
			w = (n("e17f"), n("2241")),
			A = (n("ab71"), n("58e6")),
			C = (n("bda7"), n("5e46")),
			I = (n("da3c"), n("0b33")),
			y = (n("2b28"), n("9ed2")),
			x = (n("342a"), n("1437")),
			j = (n("5d17"), n("f9bd")),
			k = (n("4ddd"), n("9f14")),
			T = (n("a44c"), n("e27c")),
			_ = (n("a909"), n("3acc")),
			D = (n("3c32"), n("417e")),
			S = (n("66b9"), n("b650")),
			q = (n("1075"), n("f600")),
			L = (n("61ae"), n("d314")),
			O = (n("09d3"), n("2d6d")),
			M = (n("e260"), n("e6cf"), n("cca6"), n("a79d"), n("1bc6")),
			B = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("div", {
					staticClass: "course-h5-test",
					class: [t.isAppIn ? "" : "no-is-app"],
					attrs: {
						id: "course-h5-test"
					}
				}, [n("card-content", [t.questionData.length > 0 ? n("template", {
					slot: "headerCotent"
				}, [n(t.currentComponent, {
					key: t.keyCom(!0),
					tag: "component",
					attrs: {
						id: t.currentComponent,
						country: t.country,
						"question-data": t.keyCom(),
						"answer-data": t.answerData[t.currentIndex],
						"is-testing": !0
					},
					on: {
						singleFn: t.selectFn
					}
				})], 1) : n("div", {
					attrs: {
						slot: "headerCotent"
					},
					slot: "headerCotent"
				})], 2)], 1)
			},
			N = [],
			F = n("1da1"),
			U = (n("a9e3"), n("159b"), n("b0c0"), n("96cf"), function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("div", {
					staticClass: "answer"
				}, [n("div", {
					staticClass: "answer-box"
				}, t._l(t.answerData, (function(e, s) {
					return n("span", {
						key: e.questionId,
						staticClass: "ball default",
						class: [t.isTesting && e.mark ? "mark" : "", t
							.isTesting && t.itemInfoCom(e, s, "done") ?
							"done" : "", t.isTesting && t.itemInfoCom(e,
								s, "current-done") ? "current-done" :
							"", t.isTesting && s === t.currentIndex && !
							e.choice[0] ? "current-not-done" : "", !t
							.isTesting && e.isCorrect ? "correct" : "",
							t.isTesting || 4 !== e.status ? "" :
							"uncorrect", t.isTesting || 4 !== e
							.status || s !== t.currentIndex ? "" :
							"current-uncorrect", t.isTesting || e
							.isCorrect || 4 == e.status ? "" :
							"uncorrect", !t.isTesting && s === t
							.currentIndex && e.isCorrect ?
							"current-correct" : "", t.isTesting || s !==
							t.currentIndex || e.isCorrect || 4 == e
							.status ? "" : "current-uncorrect"
						],
						on: {
							click: function(e) {
								return t.answerFn(s)
							}
						}
					}, [t._v(" " + t._s(s + 1) + " ")])
				})), 0)])
			}),
			P = [],
			E = {
				name: "Answer",
				props: {
					currentIndex: {
						type: Number,
						default: 0
					},
					answerData: {
						type: Array,
						default: function() {
							return []
						}
					},
					isTesting: {
						type: Boolean,
						default: !0
					}
				},
				methods: {
					answerFn: function(t) {
						this.$emit("answerFn", t)
					}
				},
				computed: {
					itemInfoCom: function() {
						var t = this;
						return function(e, n, s) {
							var a = e.questionType,
								i = e.choice,
								o = e.imgs,
								r = 4 === a || 5 === a,
								c = i.length > 0 && i[0] || o.length > 0,
								u = i.length > 0 && i[0];
							switch (s) {
								case "done":
									return r && c || !r && u ? 1 : 0;
								case "current-done":
									return n === t.currentIndex && r && c || n === t.currentIndex && u ?
										1 : 0
							}
						}
					}
				}
			},
			z = E,
			V = (n("b87a"), n("2877")),
			R = Object(V["a"])(z, U, P, !1, null, "2f237826", null),
			Y = R.exports,
			$ = n("fc0d"),
			Q = n("5530"),
			H = n("2f62"),
			J = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("section", [n("div", {
						staticClass: "course-h5-quiz-card"
					}, [t.title ? n("div", {
							staticClass: "header-title",
							class: [t.showLine ? "" : "no-showLine"]
						}, [t._v(" " + t._s(t.title) + " "), t._t("headerTitle")], 2) : t._e(), t
						.showLine ? n("hr") : t._e(), t._t("headerCotent")
					], 2), t.isHighVersion && t.isComments && t.comment && t.comment.openComment && t
					.hasDesc ? n("van-cell", {
						staticClass: "comment-cell",
						class: {
							new: t.comment.unreadRed
						},
						attrs: {
							title: t.commentCount,
							"is-link": "",
							to: t.commentUrl
						}
					}) : t._e()
				], 1)
			},
			W = [];
		n("99af"), n("4de4"), n("caad"), n("2532"), n("ac1f"), n("1276"), n("466d"), n("a15b"), n("fb6a");

		function G() {
			var t = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : "0.0.0",
				e = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "1.4.0";
			if (t && e) {
				var n = t.split("."),
					s = e.split("."),
					a = Math.min(n.length, s.length),
					i = 0,
					o = 0;
				while (i < a && 0 === (o = parseInt(n[i]) - parseInt(s[i]))) i++;
				return o = 0 !== o ? o : n.length - s.length, o >= 0
			}
			return !1
		}

		function X() {
			var t = navigator.userAgent,
				e = t.match(/AppVersion\/.*Model/),
				n = t.indexOf("Android") > -1 || t.indexOf("Adr") > -1,
				s = t.indexOf("TeacheeMaster") > -1;
			if (e && n && s) {
				var a = e[0].split("/")[1].split(" ")[0];
				return a
			}
			return "1.0"
		}
		var K = {
				name: "CardContent",
				mounted: function() {},
				computed: {
					comment: function() {
						return this.$attrs.comment || {}
					},
					commentUrl: function() {
						var t = this.$route.query,
							e = t.chapterId,
							n = t.isTry;
						return "/student/center/main/comment?id=".concat(e, "&isTry=").concat(n, "&sum=")
							.concat(this.comment.commentCount)
					},
					commentCount: function() {
						var t = Number(this.comment && this.comment.commentCount);
						return "Comments(".concat(t > 999 ? "999+" : t || 0, ")")
					},
					isHighVersion: function() {
						var t = navigator.userAgent.toLowerCase(),
							e = t.indexOf("teacheemaster") > -1;
						if (!e) return !0;
						var n = X(),
							s = G(n, "1.5.0");
						return s
					}
				},
				props: {
					title: {
						type: String,
						default: ""
					},
					showLine: {
						type: Boolean,
						default: !1
					},
					isComments: {
						type: Boolean,
						default: !0
					},
					hasDesc: {
						type: [String, Boolean],
						default: ""
					}
				},
				methods: {}
			},
			Z = K,
			tt = (n("0e8c"), Object(V["a"])(Z, J, W, !1, null, "709e1434", null)),
			et = tt.exports,
			nt = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return t.showItem(t.title) ? n("div", {
					staticClass: "course-h5-quiz-card-list"
				}, [n("div", {
					staticClass: "tip"
				}), n("div", {
					staticClass: "list-content"
				}, [t.title ? n("div", {
					staticClass: "title"
				}, [t._v(" " + t._s(t.title) + " ")]) : t._e(), t.content ? [n(
					"testContent", {
						attrs: {
							"class-name": "options-conetnt-detail title-content " + (
									"Remaining attempts:" === t.title ? "rem-info" : ""
									),
							"test-cotent": t.content
						}
					})] : t._e()], 2)]) : t._e()
			},
			st = [],
			at = n("152b"),
			it = {
				props: {
					type: {
						type: String,
						default: ""
					},
					title: {
						type: String,
						default: ""
					},
					content: {
						type: String,
						default: ""
					},
					quizData: {
						type: Object,
						default: function() {
							return null
						}
					}
				},
				components: {
					testContent: at["a"]
				},
				name: "CardList",
				data: function() {
					return {}
				},
				methods: {
					durationFilter: function() {
						var t = new Date(Number(this.quizData.validStartTime)),
							e = new Date(Number(this.quizData.validEndTime)),
							n = t.getFullYear(),
							s = t.getMonth() + 1 < 10 ? "0" + (t.getMonth() + 1) : t.getMonth() + 1,
							a = t.getDate() < 10 ? "0" + t.getDate() : t.getDate(),
							i = t.getHours() < 10 ? "0" + t.getHours() : t.getHours(),
							o = t.getMinutes() < 10 ? "0" + t.getMinutes() : t.getMinutes(),
							r = e.getHours() < 10 ? "0" + e.getHours() : e.getHours(),
							c = e.getMinutes() < 10 ? "0" + e.getMinutes() : e.getMinutes();
						return a + "/" + s + "/" + n + " " + i + ":" + o + "-" + r + ":" + c
					}
				},
				computed: {
					showItem: function() {
						var t = this;
						return function(e) {
							var n = 0;
							return "header" === t.type && "Remaining attempts:" === e && t.quizData &&
								0 === t.quizData.examType && -1 !== t.quizData.remainingAttempts && (n =
									1), "header" === t.type && "Total Time:" === e && t.quizData
								.submitMaxTime > 0 && (n = 1), t.type || (n = 1), n
						}
					}
				}
			},
			ot = it,
			rt = (n("4272"), Object(V["a"])(ot, nt, st, !1, null, null, null)),
			ct = rt.exports,
			ut = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return t.showLoading ? n("div", {
					staticClass: "course-h5-quiz-loading"
				}, [n("div", {
					staticClass: "mask"
				}), n("van-loading", {
					staticClass: "loading-info",
					attrs: {
						color: "#22BE8C"
					}
				})], 1) : t._e()
			},
			lt = [],
			dt = {
				name: "CardLoading",
				props: {
					showLoading: {
						type: Boolean,
						default: !1
					}
				},
				components: {},
				data: function() {
					return {}
				}
			},
			mt = dt,
			ft = (n("f3c4"), Object(V["a"])(mt, ut, lt, !1, null, "0ad1697b", null)),
			pt = ft.exports,
			ht = {
				data: function() {
					return {
						comment: {},
						chapterError: !1,
						courseId: -1,
						chapterId: -1,
						examId: 0,
						showLoading: !0,
						courseExpire: !1,
						expireFun: 1,
						isExpireText: "Access forbidden. Please contact school admin to unlock it."
					}
				},
				components: {
					CardContent: et,
					CardList: ct,
					CardLoading: pt
				},
				computed: Object(Q["a"])({}, Object(H["d"])("appUserStore", ["isAppIn"])),
				methods: Object(Q["a"])(Object(Q["a"])(Object(Q["a"])({}, Object(H["b"])("quizStore", [
					"submitLearnProgress"
				])), Object(H["b"])("appUserStore", ["setbackListener"])), Object(H["c"])(
					"appUserStore", ["setIsAPP"])),
				created: function() {
					this.setIsAPP()
				},
				watch: {
					"$route.query": {
						immediate: !0,
						handler: function(t) {
							t && t.courseId && (this.courseId = t.courseId), t && t.chapterId && (this
								.chapterId = t.chapterId)
						}
					}
				}
			},
			gt = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("div", {
					staticClass: "course-h5-quiz-test-header"
				}, [n("div", {
					staticClass: "left"
				}, [n("div", {
					staticClass: "question-number"
				}, [n("span", {
					staticClass: "current-number"
				}, [t._v(t._s(t.currentIndex + 1))]), n("span", {
					staticClass: "total-number"
				}, [t._v(" / " + t._s(t.questionLength))])]), t.answerData.length ? n(
					"span", {
						staticClass: "mark",
						on: {
							click: t.markFn
						}
					}, [n("i", {
						class: [this.answerData[this.currentIndex].mark ?
							"iconfont iconsign" : "iconfont iconsign-no"
						]
					})]) : t._e()]), t.isShowTimer ? n("div", {
					staticClass: "right"
				}, [n("div", {
					staticClass: "countdown",
					class: {
						"color-warn": t.timeCount < 300
					}
				}, [t._v(" " + t._s(t._f("getTime")(t.timeCount)) + " ")])]) : t._e()])
			},
			vt = [],
			bt = {
				name: "TestHeader",
				mounted: function() {},
				props: {
					timeCount: {
						type: Number,
						default: 0
					},
					isShowTimer: {
						type: Boolean,
						default: !1
					},
					examType: {
						type: Number,
						default: 0
					},
					currentIndex: {
						type: Number,
						default: 12
					},
					questionData: {
						type: Object,
						default: function() {
							return {}
						}
					},
					questionLength: {
						type: Number,
						default: 0
					},
					marked: {
						type: Boolean,
						default: !0
					},
					answerData: {
						type: Array,
						default: function() {
							return []
						}
					},
					isPause: {
						type: Boolean,
						default: !1
					}
				},
				methods: {
					onPause: function() {
						this.$emit("onPause")
					},
					markFn: function() {
						this.$emit("markFn")
					}
				},
				components: {},
				data: function() {
					return {}
				}
			},
			wt = bt,
			At = (n("bcce"), Object(V["a"])(wt, gt, vt, !1, null, "c6f69866", null)),
			Ct = At.exports,
			It = function() {
				var t = this,
					e = t.$createElement,
					s = t._self._c || e;
				return s("div", {
					staticClass: "course-h5-quiz-test-single-choice"
				}, [s("div", {
					staticClass: "topic-content"
				}, [s("div", [s("span", {
					staticClass: "topic-type"
				}, [t._v(" " + t._s(t.lang["singleChoice"]))])]), s("testContent", {
					attrs: {
						"class-name": "topic-title single-choice",
						"test-cotent": t.questionData.question
					}
				}), s("div", {
					staticClass: "mark"
				}, [t._v(" ( "), s("span", {
					staticClass: "mark-info"
				}, [t._v(t._s(t.lang["score"]) + ": " + t._s(t._f("scoreFormat")
					(t.questionData.mark)))]), s("span", {
					staticClass: "mark-penalty"
				}, [t._v(t._s(t.lang["penalty"]) + ": " + t._s(t._f(
					"scoreFormat")(t.questionData.deductMark)))]), t._v(" ) ")])], 1), s("div", {
					staticClass: "choice-content"
				}, t._l(t.questionData.options, (function(e, a) {
					return s("div", {
						key: a,
						staticClass: "choice-box choice-btn",
						class: [t.isTesting ? "" : "no-isTesting"],
						on: {
							click: function(n) {
								return t.singleFn(e.option)
							}
						}
					}, [s("div", {
						staticClass: "choice-select radio-icon-single"
					}, [s("img", {
						attrs: {
							src: e.option === t.changeValue ? n(
								"cc73") : n("c8a2")
						}
					})]), s("span", {
						staticClass: "option-info"
					}, [t._v(t._s(e.option) + " .")]), s("testContent", {
						staticClass: "option-content",
						attrs: {
							"class-name": "options-conetnt-detail choice-option",
							"test-cotent": "" + e.content
						}
					})], 1)
				})), 0)])
			},
			yt = [],
			xt = (n("3835"), n("5319"), n("d3b7"), n("25f0"), n("c1df"), n("e6b0"));
		var jt = {
				data: function() {
					return {}
				},
				components: {},
				methods: {
					initUserAnswer: function(t) {
						if (t && "{}" !== JSON.stringify(t)) {
							var e = t.choice,
								n = t.imgs,
								s = t.questionType;
							1 !== s && (this.changeValue = e[0] || ""), 1 === s && (this.changeValue = e ||
								[]), 4 !== s && 5 !== s || (this.allImgList = n)
						}
					}
				},
				filters: {
					scoreFormat: xt["a"]
				},
				created: function() {},
				watch: {
					answerData: {
						handler: function(t) {
							t && this.initUserAnswer(t)
						},
						immediate: !0,
						deep: !0
					}
				}
			},
			kt = {
				name: "SingleChoice",
				mounted: function() {},
				components: {
					testContent: at["a"]
				},
				props: {
					questionData: {
						type: Object,
						default: function() {
							return {}
						}
					},
					isTesting: {
						type: Boolean,
						default: !0
					},
					answerData: {
						type: Object,
						default: function() {
							return {}
						}
					}
				},
				data: function() {
					return {
						changeValue: ""
					}
				},
				mixins: [jt],
				computed: {
					lang: function() {
						return this.$store.getters.getLang
					}
				},
				methods: {
					singleFn: function(t) {
						this.isTesting && (this.changeValue === t ? this.changeValue = "" : this
							.changeValue = t, this.$emit("singleFn", this.changeValue))
					}
				}
			},
			Tt = kt,
			_t = (n("7828"), Object(V["a"])(Tt, It, yt, !1, null, null, null)),
			Dt = _t.exports,
			St = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("div", {
					staticClass: "course-h5-quiz-test-answer-card"
				}, [n("ul", {
					staticClass: "list"
				}, t._l(t.answerCardList, (function(e, s) {
					return n("li", {
						key: s,
						staticClass: "item",
						class: [t.currentNumber == s ? "currentItem" : t
							.currentNumber > s ? "answered" : "no-answer"
						]
					}, [t._v(" " + t._s(s) + " ")])
				})), 0), n("van-button", {
					staticClass: "quiz-card-btn submit",
					attrs: {
						round: ""
					}
				}, [t._v(" Early submit ")])], 1)
			},
			qt = [],
			Lt = {
				name: "AnswerCard",
				mounted: function() {},
				props: {
					answerCardList: {
						type: Array,
						default: function() {
							return [1, 2, 3, 4, 5, 6, 7]
						}
					},
					currentNumber: {
						type: Number,
						default: 1
					}
				},
				components: {},
				data: function() {
					return {}
				}
			},
			Ot = Lt,
			Mt = (n("a4f3"), Object(V["a"])(Ot, St, qt, !1, null, "dbe74222", null)),
			Bt = (Mt.exports, function() {
				var t = this,
					e = t.$createElement,
					s = t._self._c || e;
				return s("div", {
					staticClass: "course-h5-quiz-test-multiple-choice"
				}, [s("div", {
					staticClass: "topic-content"
				}, [s("div", [s("span", {
					staticClass: "topic-type"
				}, [t._v(t._s(t.lang["multipleChoice"]))])]), s("testContent", {
					attrs: {
						"class-name": "topic-title multiple-choice",
						"test-cotent": t.questionData.question
					}
				}), s("div", {
					staticClass: "mark"
				}, [t._v(" ( "), s("span", {
					staticClass: "mark-info"
				}, [t._v(t._s(t.lang["score"]) + ": " + t._s(t._f(
					"scoreFormat")(t.questionData.mark)))]), s("span", {
					staticClass: "mark-penalty"
				}, [t._v(t._s(t.lang["penalty"]) + ": " + t._s(t._f(
					"scoreFormat")(t.questionData.deductMark)))]), t._v(" ) ")])], 1), s(
				"div", {
					staticClass: "multiple-choice-content"
				}, [s("van-checkbox-group", {
					staticClass: "multiple-choice-btn",
					class: [t.isTesting ? "" : "no-isTesting"],
					on: {
						change: t.multipleFn
					},
					model: {
						value: t.changeValue,
						callback: function(e) {
							t.changeValue = e
						},
						expression: "changeValue"
					}
				}, t._l(t.questionData.options, (function(e, a) {
					return s("van-checkbox", {
						key: a,
						attrs: {
							shape: "square",
							"checked-color": "#22BE8C",
							name: e.option
						},
						scopedSlots: t._u([{
							key: "icon",
							fn: function(a) {
								return [s("img", {
									staticClass: "mutiple-checked-info",
									attrs: {
										src: a
											.checked ?
											n(
												"0032") :
											n(
												"59e1")
									}
								}), s(
									"span", {
										staticClass: "option-info"
									}, [t
										._v(t
											._s(e
												.option
												) +
											" ."
											)
									])]
							}
						}], null, !0)
					}, [s("testContent", {
						attrs: {
							"class-name": "options-conetnt-detail choice-option",
							"test-cotent": "" + e.content
						}
					})], 1)
				})), 1)], 1)])
			}),
			Nt = [],
			Ft = {
				name: "MultipleChoice",
				mounted: function() {},
				components: {
					testContent: at["a"]
				},
				props: {
					questionData: {
						type: Object,
						default: function() {
							return {}
						}
					},
					isTesting: {
						type: Boolean,
						default: !0
					},
					answerData: {
						type: Object,
						default: function() {
							return {}
						}
					}
				},
				data: function() {
					return {
						changeValue: []
					}
				},
				computed: {
					lang: function() {
						return this.$store.getters.getLang
					}
				},
				mixins: [jt],
				methods: {
					multipleFn: function() {
						this.$emit("singleFn", this.changeValue)
					}
				}
			},
			Ut = Ft,
			Pt = (n("5412"), Object(V["a"])(Ut, Bt, Nt, !1, null, "c3192eae", null)),
			Et = Pt.exports,
			zt = function() {
				var t = this,
					e = t.$createElement,
					s = t._self._c || e;
				return s("div", {
					staticClass: "course-h5-quiz-test-single-choice"
				}, [s("div", {
					staticClass: "topic-content"
				}, [s("div", [s("span", {
					staticClass: "topic-type"
				}, [t._v(" " + t._s(t.lang["trueOrFalse"]))])]), s("testContent", {
					attrs: {
						"class-name": "topic-title true-or-false",
						"test-cotent": t.questionData.question
					}
				}), s("div", {
					staticClass: "mark"
				}, [t._v(" ( "), s("span", {
					staticClass: "mark-info"
				}, [t._v(t._s(t.lang["score"]) + ": " + t._s(t._f("scoreFormat")
					(t.questionData.mark)))]), s("span", {
					staticClass: "mark-penalty"
				}, [t._v(t._s(t.lang["penalty"]) + ": " + t._s(t._f(
					"scoreFormat")(t.questionData.deductMark)))]), t._v(" ) ")])], 1), s("div", {
					staticClass: "choice-content"
				}, t._l(t.questionData.options, (function(e, a) {
					return s("div", {
						key: a,
						staticClass: "choice-box choice-btn",
						class: [t.isTesting ? "" : "no-isTesting"],
						on: {
							click: function(n) {
								return t.singleFn(e.option)
							}
						}
					}, [s("img", {
						staticClass: "choice-select radio-icon-single",
						attrs: {
							src: e.option === t.changeValue ? n(
								"cc73") : n("c8a2")
						}
					}), s("span", {
						staticClass: "option-info"
					}, [t._v(t._s(e.option) + " .")]), s("testContent", {
						staticClass: "option-content",
						attrs: {
							"class-name": "options-conetnt-detail choice-option",
							"test-cotent": "" + e.content
						}
					})], 1)
				})), 0)])
			},
			Vt = [],
			Rt = {
				name: "TrueOrFalse",
				mounted: function() {},
				components: {
					testContent: at["a"]
				},
				props: {
					questionData: {
						type: Object,
						default: function() {
							return {}
						}
					},
					isTesting: {
						type: Boolean,
						default: !0
					},
					answerData: {
						type: Object,
						default: function() {
							return {}
						}
					}
				},
				computed: {
					lang: function() {
						return this.$store.getters.getLang
					}
				},
				data: function() {
					return {
						changeValue: ""
					}
				},
				mixins: [jt],
				methods: {
					singleFn: function(t) {
						this.isTesting && (this.changeValue === t ? this.changeValue = "" : this
							.changeValue = t, this.$emit("singleFn", this.changeValue))
					}
				}
			},
			Yt = Rt,
			$t = (n("a541"), Object(V["a"])(Yt, zt, Vt, !1, null, "86c1dcca", null)),
			Qt = $t.exports,
			Ht = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return "{}" !== JSON.stringify(t.explanationData) ? n("div", {
					ref: "renderContent",
					staticClass: "explanation"
				}, [n("div", {
					staticClass: "score"
				}, [n("span", {
					staticClass: "explanation-info"
				}, [t._v("Your Score:")]), n("span", {
					class: {
						"not-corrected": 4 === t.explanationData.isReleased, warn: 4 !=
							t.explanationData.isReleased && t.explanationData.score <=
							0, success: 4 != t.explanationData.isReleased && t
							.explanationData.score > 0
					}
				}, [t._v(" " + t._s(t._f("scoreFormat")(4 === t.explanationData
					.isReleased ? t.lang["questionStatus"] : t
					.explanationData.score)))])]), n("div", {
					staticClass: "correct-answer"
				}, [n("span", {
					staticClass: "explanation-info"
				}, [t._v("Correct Answer:")]), t._v(" "), n("div", {
					staticClass: "answer-detail",
					class: {
						active: !t.questionType
					},
					domProps: {
						innerHTML: t._s(t.testDetail)
					}
				})]), n("div", {
					staticClass: "self-answer"
				}, [n("div", {
					staticClass: "answer"
				}, [n("span", {
						staticClass: "explanation-info"
					}, [t._v("Your Answer:")]), t.toggleAnswerSkip ? n("span", {
						staticClass: "skipped-question"
					}, [t._v("You have skipped the question ")]) : t._e(), n("div", {
						staticClass: "answer-detail",
						class: {
							active: !t.questionType
						},
						domProps: {
							innerHTML: t._s(t.selAnswerDetail)
						}
					}), t.questionType || t.toggleAnswerSkip ? t._e() : n("span", {
						staticClass: "answer-icon iconfont",
						class: {
							"icon-close close-tip": !t.explanationData.isCorrect,
								"icon-completed completed-tip": t.explanationData
								.isCorrect
						}
					}), t.explanationData.userImgs && t.explanationData.userImgs
					.length > 0 ? n("div", {
						staticClass: "img-list"
					}, t._l(t.explanationData.userImgs, (function(e, s) {
						return n("div", {
							key: s,
							staticClass: "img-item",
							on: {
								click: function(n) {
									return n.stopPropagation(),
										t.previewImg(e)
								}
							}
						}, [n("img", {
							attrs: {
								src: e
							}
						})])
					})), 0) : t._e()
				])]), n("div", {
					staticClass: "dec"
				}, [t.explanationData.solution || t.explanationData.videoInfo && t
					.explanationData.videoInfo.length ? [n("div", {
							staticClass: "text-solution"
						}, [t.explanationData.solution ? n("p", {
							staticClass: "explanation-info"
						}, [t._v(" Text Solution: ")]) : t._e(), n("testContent", {
							attrs: {
								"class-name": "options-conetnt-detail",
								"test-cotent": t.explanationData.solution
							}
						})], 1), t.explanationData.videoInfo && t.explanationData.videoInfo
						.length ? n("div", {
							staticClass: "video-solution"
						}, [n("p", {
							staticClass: "explanation-info"
						}, [t._v(" Video Solution ")])]) : t._e()
					] : [t._v(" No solution available. ")]
				], 2), 4 !== t.explanationData.isReleased && this.explanationData.feedback ? n(
					"div", {
						staticClass: "score feedback"
					}, [n("span", {
						staticClass: "explanation-info"
					}, [t._v("Feedback:")]), n("p", {
						staticClass: "feedback-detail"
					}, [t._v(" " + t._s(t.strFormData) + " ")])]) : t._e(), n("div", {
					staticClass: "knowledge-overview"
				}, [n("div", {
					staticClass: "explanation-info item-overview"
				}, [t._v(" Correct Rate: "), n("span", {
					staticClass: "success"
				}, [t._v(t._s((t.explanationData.accuracy / 100).toFixed(1) +
					"%"))])]), n("p", {
					staticClass: "explanation-info item-overview"
				}, [t._v(" Difficulty Level：" + t._s(t._f("difficdultLeveInfo")(t
					.explanationData.difficultyLevel)) + " ")]), n("div", {
					staticClass: "explanation-info item-overview"
				}, [n("p", [t._v("Concept: "), t.explanationData.topic && 0 !== t
						.explanationData.topic.length ? t._e() : n("span", [t._v(
							"-")])
					]), t.explanationData.topic && t.explanationData.topic.length > 0 ?
					n("ul", {
						staticClass: "topic-box-wrapper"
					}, t._l(t.explanationData.topic, (function(e, s) {
						return n("li", {
							key: s,
							staticClass: "topic-item"
						}, [t._v(" " + t._s(e) + " ")])
					})), 0) : t._e()
				])]), n("img-preview", {
					attrs: {
						"img-source": t.imgSource,
						"show-preview": t.showPreview
					},
					on: {
						"update:showPreview": function(e) {
							t.showPreview = e
						},
						"update:show-preview": function(e) {
							t.showPreview = e
						}
					}
				})], 1) : t._e()
			},
			Jt = [],
			Wt = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return t.showPreview ? n("div", {
					staticClass: "img-preview",
					on: {
						click: t.closePreview
					}
				}, [n("div", {
					staticClass: "img-box"
				}, [n("img", {
					attrs: {
						src: t.imgSource
					}
				})])]) : t._e()
			},
			Gt = [],
			Xt = {
				name: "ImgPreview",
				props: {
					imgSource: {
						type: String,
						default: ""
					},
					showPreview: {
						type: Boolean,
						default: !1
					}
				},
				methods: {
					closePreview: function() {
						this.$emit("update:showPreview", !1)
					}
				},
				watch: {
					showPreview: {
						immediate: !0,
						handler: function(t) {
							if (t) {
								var e = function(t) {
									t.preventDefault()
								};
								document.body.style.overflow = "hidden", document.addEventListener(
									"touchmove", e, !1)
							} else {
								var n = function(t) {
									t.preventDefault()
								};
								document.body.style.overflow = "", document.removeEventListener("touchmove",
									n, !1)
							}
						}
					}
				}
			},
			Kt = Xt,
			Zt = (n("ecff"), Object(V["a"])(Kt, Wt, Gt, !1, null, "2e8d1d92", null)),
			te = Zt.exports,
			ee = {
				name: "Explanation",
				components: {
					testContent: at["a"],
					ImgPreview: te
				},
				props: {
					explanationData: {
						type: Object,
						default: function() {
							return {}
						}
					}
				},
				data: function() {
					return {
						imgSource: "",
						showDesc: !1,
						showPreview: !1,
						toggleAnswerSkip: !1,
						activeNames: [],
						list: ["https://teachee-site.s3.ap-south-1.amazonaws.com/frontend/pages/1599552707331_f026764a.jpeg",
							"https://teachee-site.s3.ap-south-1.amazonaws.com/frontend/pages/1599552707324_9c7c3cb0.jpeg",
							"https://teachee-site.s3.ap-south-1.amazonaws.com/frontend/pages/1599552707331_f026764a.jpeg",
							"https://teachee-site.s3.ap-south-1.amazonaws.com/frontend/pages/1599552707331_f026764a.jpeg",
							"https://teachee-site.s3.ap-south-1.amazonaws.com/frontend/pages/1599552707331_f026764a.jpeg",
							"https://teachee-site.s3.ap-south-1.amazonaws.com/frontend/pages/1599552707331_f026764a.jpeg",
							"https://teachee-site.s3.ap-south-1.amazonaws.com/frontend/pages/1599552707331_f026764a.jpeg"
						]
					}
				},
				filters: {
					topicFormate: function(t) {
						return t ? t.length > 100 ? t.substring(0, 100) + "..." : t : ""
					},
					difficdultLeveInfo: function(t) {
						var e = {
							0: "-",
							1: "Easy",
							2: "Medium",
							3: "Hard"
						};
						return e[Number(t)]
					}
				},
				methods: {
					previewImg: function(t) {
						this.showPreview = !0, this.imgSource = t
					}
				},
				watch: {
					explanationData: {
						deep: !0,
						immediate: !0,
						handler: function(t) {
							var e = t.isReleased;
							this.toggleAnswerSkip = 3 === e
						}
					}
				},
				computed: {
					lang: function() {
						return this.$store.getters.getLang
					},
					selAnswerDetail: function() {
						var t = this.explanationData,
							e = t.userChoice,
							n = t.questionType;
						return (null === e || void 0 === e ? void 0 : e.length) > 0 ? 4 === n || 5 === n ?
							e[0] : e.join("") : ""
					},
					questionType: function() {
						var t = this.explanationData.questionType;
						return 4 === t || 5 === t ? 1 : 0
					},
					testDetail: function() {
						var t = this.explanationData,
							e = t.questionType,
							n = t.choice;
						return (null === n || void 0 === n ? void 0 : n.length) > 0 ? 3 === e || 4 === e ||
							5 === e ? n : n.join("") : ""
					},
					strFormData: function() {
						if (this.explanationData.feedback) {
							var t, e = (null === (t = this.explanationData) || void 0 === t ? void 0 : t
								.feedback) || "";
							return e.replace(/#@#/g, ";")
						}
						return ""
					}
				}
			},
			ne = ee,
			se = (n("2e33"), Object(V["a"])(ne, Ht, Jt, !1, null, "4bb6fe43", null)),
			ae = (se.exports, function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("div", {
					staticClass: "course-h5-quiz-test-numerical"
				}, [n("div", {
					staticClass: "topic-content"
				}, [n("div", [n("span", {
					staticClass: "topic-type"
				}, [t._v(t._s(t.lang["numberical"]))])]), n("testContent", {
					attrs: {
						"class-name": "topic-title numerical",
						type: t.questionData.questionType,
						"test-cotent": t.questionData.question
					}
				}), n("div", {
					staticClass: "mark"
				}, [t._v(" ( "), n("span", {
					staticClass: "mark-info"
				}, [t._v(t._s(t.lang["score"]) + ": " + t._s(t._f(
					"scoreFormat")(t.questionData.mark)))]), n("span", {
					staticClass: "mark-penalty"
				}, [t._v(t._s(t.lang["penalty"]) + ": " + t._s(t._f(
					"scoreFormat")(t.questionData.deductMark)))]), t._v(" ) ")])], 1), n(
				"div", {
					directives: [{
						name: "show",
						rawName: "v-show",
						value: t.isTesting,
						expression: "isTesting"
					}],
					staticClass: "numerical-content"
				}, [n("van-field", {
					ref: "inputBtn",
					staticClass: "show-word__limit show-word__limit_text",
					attrs: {
						type: "text",
						"show-word-limit": "",
						maxlength: "10",
						autofocus: !0,
						placeholder: t.lang["enterAnswer"]
					},
					on: {
						blur: t.blurFun
					},
					model: {
						value: t.changeValue,
						callback: function(e) {
							t.changeValue = e
						},
						expression: "changeValue"
					}
				})], 1)])
			}),
			ie = [];

		function oe(t, e, n) {
			var s = t.indexOf(e);
			return -1 === s ? t : "-" === e && 0 !== s ? t.slice(0, s) : t.slice(0, s + 1) + t.slice(s).replace(
				n, "")
		}

		function re(t) {
			var e = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
				n = !(arguments.length > 2 && void 0 !== arguments[2]) || arguments[2];
			t = e ? oe(t, ".", /\./g) : t.split(".")[0], t = n ? oe(t, "-", /-/g) : t.replace(/-/, "");
			var s = e ? /[^-0-9.]/g : /[^-0-9]/g;
			return t.replace(s, "")
		}
		var ce = {
				name: "Numerical",
				components: {
					testContent: at["a"]
				},
				props: {
					questionData: {
						type: Object,
						default: function() {
							return {}
						}
					},
					isTesting: {
						type: Boolean,
						default: !0
					},
					answerData: {
						type: Object,
						default: function() {
							return {}
						}
					}
				},
				mixins: [jt],
				data: function() {
					return {
						changeValue: ""
					}
				},
				watch: {
					changeValue: function(t) {
						if (t) {
							t.length > 10 && (t = t.slice(0, 10));
							var e = re(t, !0, !0);
							this.changeValue = e
						}
					}
				},
				mounted: function() {
					this.$nextTick((function() {
						document.getElementsByClassName("van-field__control")[0].focus()
					}))
				},
				computed: {
					lang: function() {
						return this.$store.getters.getLang
					}
				},
				methods: {
					changNumber: function(t) {
						if ("" !== t) {
							t.length > 10 && (t = t.slice(0, 10));
							var e = re(t, !0, !0);
							this.changeValue = e
						}
					},
					blurFun: function() {
						this.$emit("singleFn", this.changeValue)
					}
				}
			},
			ue = ce,
			le = (n("3e06"), Object(V["a"])(ue, ae, ie, !1, null, "7430d620", null)),
			de = le.exports,
			me = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("div", {
					staticClass: "course-h5-quiz-test-numerical"
				}, [n("div", {
						staticClass: "topic-content"
					}, [n("div", [n("span", {
						staticClass: "topic-type"
					}, [t._v(t._s(t.lang["fillInTheBlank"]))])]), n("testContent", {
						attrs: {
							"class-name": "topic-title fill-the-blank",
							"test-cotent": t.questionData.question
						}
					}), n("div", {
						staticClass: "mark"
					}, [t._v(" ( "), n("span", {
						staticClass: "mark-info"
					}, [t._v(t._s(t.lang["score"]) + ": " + t._s(t._f("scoreFormat")
						(t.questionData.mark)))]), n("span", {
						staticClass: "mark-penalty"
					}, [t._v(t._s(t.lang["penalty"]) + ": " + t._s(t._f(
						"scoreFormat")(t.questionData.deductMark)))]), t._v(" ) ")])], 1), t.isTesting ?
					n("div", {
						staticClass: "numerical-content"
					}, [n("imgUpload", {
						attrs: {
							maxlength: 100,
							"limit-number": 5,
							"init-value": t.changeValue,
							"default-show-number": 5,
							"all-img-list": t.allImgList
						},
						on: {
							changeInputImg: t.changeInputImg
						}
					})], 1) : t._e()
				])
			},
			fe = [],
			pe = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("div", {
					staticClass: "upload-img"
				}, [n("div", {
					staticClass: "textarea-content"
				}, [n("van-field", {
					attrs: {
						rows: "2",
						autosize: "",
						type: "textarea",
						autofocus: !0,
						maxlength: t.maxlength,
						placeholder: t.lang["enterAnswer"],
						"show-word-limit": ""
					},
					on: {
						input: t.changeInput,
						blur: t.blurFn
					},
					model: {
						value: t.answer,
						callback: function(e) {
							t.answer = e
						},
						expression: "answer"
					}
				})], 1), n("div", {
					staticClass: "upload-img-btn"
				}, [n("div", {
					ref: "imglist",
					staticClass: "img-list"
				}, t._l(t.showImgList, (function(e, s) {
					return n("div", {
						key: s,
						staticClass: "img-item",
						on: {
							click: function(n) {
								return n.stopPropagation(), t
									.previewImg(e)
							}
						}
					}, [n("img", {
						attrs: {
							width: "100%",
							src: e
						}
					}), t.showAll(s) ? t._e() : n("div", {
						staticClass: "delete-btn",
						on: {
							click: function(n) {
								return n.stopPropagation(),
									t.deleteBtn(e)
							}
						}
					}), t.showAll(s) ? n("div", {
						staticClass: "more-info",
						on: {
							click: function(e) {
								return e.stopPropagation(),
									t.allImgShow(e)
							}
						}
					}, [t._v(" +" + t._s(t.allImgLists.length -
						t.showImgList.length) + " ")]) : t._e()])
				})), 0), t.allImgLists.length < t.limitNumber ? n("div", {
					staticClass: "btn"
				}, [t.isAppIn ? [n("div", {
					staticClass: "app-upload-img-btn",
					on: {
						click: function(e) {
							return e.stopPropagation(), t
								.appUploadImgBtn(e)
						}
					}
				}, [n("i", {
					staticClass: "el-icon-plus"
				})])] : [n("el-upload", {
					ref: "upload",
					staticClass: "upload-btn-el",
					attrs: {
						action: "",
						accept: "image/*",
						limit: t.limitNumber,
						multiple: !0,
						capture: "camera",
						"list-type": "picture-card",
						"show-file-list": !1,
						"on-exceed": t.exceedFun,
						"on-change": t.handleAvatarSuccess,
						"file-list": t.fileListMap,
						"auto-upload": !1
					}
				}, [n("i", {
					staticClass: "el-icon-plus",
					attrs: {
						slot: "default"
					},
					slot: "default"
				})])]], 2) : t._e()]), n("img-preview", {
					attrs: {
						"img-source": t.imgSource,
						"show-preview": t.showPreview
					},
					on: {
						"update:showPreview": function(e) {
							t.showPreview = e
						},
						"update:show-preview": function(e) {
							t.showPreview = e
						}
					}
				})], 1)
			},
			he = [],
			ge = (n("d81d"), n("c740"), n("a434"), n("5cc6"), n("9a8c"), n("a975"), n("735e"), n("c1ac"), n(
				"d139"), n("3a7b"), n("d5d6"), n("82f8"), n("e91f"), n("60bd"), n("5f96"), n("3280"), n(
				"3fcc"), n("ca91"), n("25a1"), n("cd26"), n("3c5d"), n("2954"), n("649e"), n("219c"), n(
				"170b"), n("b39a"), n("72f7"), {
				name: "ImgUpload",
				props: {
					maxlength: {
						type: Number,
						default: 100
					},
					limitNumber: {
						type: Number,
						default: 5
					},
					defaultShowNumber: {
						type: Number,
						default: 5
					},
					allImgList: {
						type: Array,
						default: function() {
							return []
						}
					},
					initValue: {
						type: String,
						default: ""
					}
				},
				data: function() {
					return {
						keyboardHeight: 0,
						allImgLists: [],
						showPreview: !1,
						imgSource: "",
						answer: "",
						overImgNumber: 0,
						showImgList: [],
						clickFlag: !1,
						isAppIn: !0
					}
				},
				created: function() {
					this.setIsAPP()
				},
				mounted: function() {
					this.init()
				},
				components: {
					ImgPreview: te
				},
				computed: {
					lang: function() {
						return this.$store.getters.getLang
					},
					showAll: function() {
						var t = this;
						return function(e) {
							return t.allImgLists.length >= t.defaultShowNumber && e + 1 === t
								.defaultShowNumber && t.allImgLists.length - t.showImgList.length >
								0
						}
					},
					fileListMap: function() {
						if (this.allImgList.length < 1) return [];
						var t = [];
						return this.allImgList.map((function(e) {
							var n = e.split("/"),
								s = n.length - 1,
								a = n[s].split(".")[0];
							t.push({
								name: a,
								url: e
							})
						})), t
					}
				},
				methods: Object(Q["a"])(Object(Q["a"])(Object(Q["a"])({}, Object(H["b"])("appUserStore", [
					"appAddPicture"
				])), Object(H["c"])("appUserStore", ["setIsAPP"])), {}, {
					init: function() {
						this.answer = this.initValue, this.allImgLists = this.allImgList, this
							.showImgList = this.allImgLists.length > this.defaultShowNumber ?
							this.allImgLists.slice(0, this.defaultShowNumber) : this.allImgLists
							.slice(), this.$nextTick((function() {
								document.getElementsByClassName("van-field__control")[0]
									.focus()
							}))
					},
					previewImg: function(t) {
						this.showPreview = !0, this.imgSource = t
					},
					appUploadImgBtn: function() {
						var t = this,
							e = this.limitNumber - this.allImgLists.length;
						if (e <= 0) return this.clickFlag = !0, void(this.clickFlag && this
							.exceedFun());
						this.appAddPicture(e).then((function(e) {
							var n = JSON.parse(e.picture);
							n && n.length > 0 && (t.allImgLists = t.allImgLists
								.concat(n), t.showImgList = t.allImgLists
								.length > t.defaultShowNumber ? t.allImgLists
								.slice(0, t.defaultShowNumber) : t.allImgLists
								.slice()), t.$emit("changeInputImg", {
								inputV: t.answer,
								imgList: t.allImgLists
							})
						})).catch((function(e) {
							console.log(e), t.$toast("Upload failed.")
						}))
					},
					allImgShow: function() {
						this.showImgList = this.allImgLists
					},
					deleteBtn: function(t) {
						var e = this.allImgLists.findIndex((function(e) {
							return e === t
						}));
						this.allImgLists.splice(e, 1), this.showImgList = this.allImgLists
							.slice(0, this.defaultShowNumber), this.$emit("changeInputImg", {
								inputV: this.answer,
								imgList: this.allImgLists
							})
					},
					exceedFun: function() {
						this.$toast("You could only upload up ".concat(this.limitNumber,
							" photos."))
					},
					handleAvatarSuccess: function(t) {
						var e = this;
						return Object(F["a"])(regeneratorRuntime.mark((function n() {
							var s;
							return regeneratorRuntime.wrap((function(n) {
								while (1) switch (n.prev = n.next) {
									case 0:
										return e.$toast.loading(
												"uploading"), n
											.next = 3, e
											.photoCompress(t);
									case 3:
										s = n.sent, s.name = t
											.raw.name, e
											.uploadFunc(s);
									case 6:
									case "end":
										return n.stop()
								}
							}), n)
						})))()
					},
					uploadFunc: function() {},
					changeInput: function(t) {
						"" !== t && (t.length > this.maxlength && (t = t.slice(0, this
							.maxlength)), this.answer = t)
					},
					blurFn: function() {
						this.$emit("changeInputImg", {
							inputV: this.answer,
							imgList: this.showImgList
						})
					},
					convertBase64UrlToBlob: function(t) {
						var e = t.split(","),
							n = e[0].match(/:(.*?);/)[1],
							s = atob(e[1]),
							a = s.length,
							i = new Uint8Array(a);
						while (a--) i[a] = s.charCodeAt(a);
						return new Blob([i], {
							type: n
						})
					},
					photoCompress: function(t) {
						var e = this;
						return Object(F["a"])(regeneratorRuntime.mark((function n() {
							var s, a;
							return regeneratorRuntime.wrap((function(n) {
								while (1) switch (n.prev = n.next) {
									case 0:
										return s =
											new FileReader, n
											.next = 3, e
											.beforeAvatarUpload(
												t, 300, 300);
									case 3:
										return a = n.sent, s
											.readAsDataURL(a), n
											.abrupt("return",
												new Promise((
													function(
														t) {
														s.onload =
															function(
																n
																) {
																var s =
																	e
																	.convertBase64UrlToBlob(
																		n
																		.target
																		.result
																		);
																t(s)
															}
													})));
									case 6:
									case "end":
										return n.stop()
								}
							}), n)
						})))()
					},
					beforeAvatarUpload: function(t, e, n) {
						var s = this,
							a = t.url,
							i = .5;
						return new Promise((function(o) {
							var r = new Image;
							r.src = a, r.onload = function() {
								var a = document.createElement("canvas"),
									c = r.width,
									u = r.height,
									l = isNaN(e) ? 0 : e,
									d = isNaN(n) ? 0 : n;
								0 === l && u > d && (c = Math.round(c *= d / u),
										u = d), 0 === d && c > l && (u = Math
										.round(u *= l / c), c = l), a.width = c,
									a.height = u;
								var m = a.getContext("2d");
								a.width = c, a.height = u, m.drawImage(r, 0, 0,
									c, u);
								var f = a.toDataURL(t.raw.type, i),
									p = s.convertBase64UrlToBlob(f);
								o(p)
							}
						}))
					}
				})
			}),
			ve = ge,
			be = (n("4f1e"), Object(V["a"])(ve, pe, he, !1, null, "4ac161e2", null)),
			we = be.exports,
			Ae = {
				name: "FillInTheBlank",
				components: {
					testContent: at["a"],
					imgUpload: we
				},
				mixins: [jt],
				props: {
					questionData: {
						type: Object,
						default: function() {
							return {}
						}
					},
					answerData: {
						type: Object,
						default: function() {
							return {}
						}
					},
					isTesting: {
						type: Boolean,
						default: !0
					}
				},
				data: function() {
					return {
						changeValue: "",
						allImgList: []
					}
				},
				mounted: function() {},
				computed: {
					lang: function() {
						return this.$store.getters.getLang
					}
				},
				methods: {
					changeInputImg: function(t) {
						this.$emit("singleFn", t)
					}
				}
			},
			Ce = Ae,
			Ie = (n("ba96"), Object(V["a"])(Ce, me, fe, !1, null, "7b0fe903", null)),
			ye = Ie.exports,
			xe = function() {
				var t = this,
					e = t.$createElement,
					n = t._self._c || e;
				return n("div", {
					staticClass: "course-h5-quiz-test-numerical"
				}, [n("div", {
						staticClass: "topic-content"
					}, [n("div", [n("span", {
						staticClass: "topic-type"
					}, [t._v(t._s(t.lang["subjective"]))])]), n("testContent", {
						attrs: {
							"class-name": "topic-title subjective",
							"test-cotent": t.questionData.question
						}
					}), n("div", {
						staticClass: "mark"
					}, [t._v(" ( "), n("span", {
						staticClass: "mark-info"
					}, [t._v(t._s(t.lang["score"]) + ": " + t._s(t._f("scoreFormat")
						(t.questionData.mark)))]), n("span", {
						staticClass: "mark-penalty"
					}, [t._v(t._s(t.lang["penalty"]) + ": " + t._s(t._f(
						"scoreFormat")(t.questionData.deductMark)))]), t._v(" ) ")])], 1), t.isTesting ?
					n("div", {
						staticClass: "numerical-content"
					}, [n("imgUpload", {
						attrs: {
							maxlength: 5e3,
							"limit-number": 5,
							"init-value": t.changeValue,
							"default-show-number": 5,
							"all-img-list": t.allImgList
						},
						on: {
							changeInputImg: t.changeInputImg
						}
					})], 1) : t._e()
				])
			},
			je = [],
			ke = {
				name: "Subjective",
				mounted: function() {},
				components: {
					testContent: at["a"],
					imgUpload: we
				},
				props: {
					questionData: {
						type: Object,
						default: function() {
							return {}
						}
					},
					answerData: {
						type: Object,
						default: function() {
							return {}
						}
					},
					isTesting: {
						type: Boolean,
						default: !0
					},
					userAnswerData: {
						type: Object,
						default: function() {
							return {}
						}
					}
				},
				data: function() {
					return {
						changeValue: "",
						allImgList: []
					}
				},
				computed: {
					lang: function() {
						return this.$store.getters.getLang
					}
				},
				mixins: [jt],
				methods: {
					changeInputImg: function(t) {
						this.$emit("singleFn", t)
					}
				}
			},
			Te = ke,
			_e = (n("5720"), Object(V["a"])(Te, xe, je, !1, null, "c00d54f8", null)),
			De = _e.exports,
			Se = {
				name: "Test",
				data: function() {
					return {
						country: "",
						submitTimeDetail: 0,
						remainingTime: 0,
						isSubmitInfo: !1,
						endAnswer: !0,
						examType: 0,
						validEndTime: 0,
						setIntervalTimer: null,
						showSubmit: !1,
						isGiveUp: !1,
						submitAnswer: !1,
						isPause: !1,
						currentIndex: 0,
						startTime: 0,
						answerData: [],
						answerDataForApp: {},
						submitAnswerData: {
							questionLen: 0,
							answereQues: 0,
							skipQues: 0
						},
						timer: null,
						count: 0,
						incrementCount: 0,
						isShowTimer: !1,
						notSubmitModal: !1,
						userAnswerId: "",
						examInfo: {
							examId: "",
							examType: 0,
							submitMaxTime: 0,
							submitMinTime: 0
						},
						questionData: [],
						isClickPause: !1,
						flag: !1,
						isAnswer: !1,
						isSubmit: !1
					}
				},
				components: {
					TestHeader: Ct,
					SingleChoice: Dt,
					Answer: Y,
					MultipleChoice: Et,
					TrueOrFalse: Qt,
					Numerical: de,
					FillInTheBlank: ye,
					Subjective: De
				},
				mixins: [ht],
				created: function() {
					var t = this;
					this.$route.query.country && this.$store.commit("changeCountry", this.$route.query
						.country), window.setQ = function(e) {
						var n = e,
							s = n.datas,
							a = n.exam;
						for (var i in t.questionData = [], t.questionData = s, t.examInfo) t.examInfo[
							i] = a[i] ? a[i] : t.examInfo[i];
						t.getUserAnwser(t.questionData), t.questionData.length && t.questionData[0]
							.questionInfo && (t.questionData[0].questionInfo.userAnswer ? (t
								.questionData[0].questionInfo.userAnswer.questionType = t
								.questionData[0].questionInfo.questionType, t.answerData = [t
									.questionData[0].questionInfo.userAnswer
								]) : t.answerData = [{
								choice: [],
								imgs: [],
								questionType: t.questionData[0].questionInfo.questionType,
								questionId: t.questionData[0].questionInfo.questionId
							}])
					}, window.getA = function() {
						return t.computerSendAnswerParams()
					}
				},
				mounted: function() {
					return Object(F["a"])(regeneratorRuntime.mark((function t() {
						return regeneratorRuntime.wrap((function(t) {
							while (1) switch (t.prev = t.next) {
								case 0:
								case 1:
								case "end":
									return t.stop()
							}
						}), t)
					})))()
				},
				computed: {
					isShowIsAnswer: {
						get: function() {
							var t = this.examInfo.examType;
							return 1 === t && !this.isSubmit && this.isAnswer
						}
					},
					currentComponent: function() {
						if (!this.keyCom()) return "";
						var t = {
							0: "SingleChoice",
							1: "MultipleChoice",
							2: "TrueOrFalse",
							3: "Numerical",
							4: "FillInTheBlank",
							5: "Subjective"
						};
						return t[this.keyCom().questionType]
					},
					submitTime: function() {
						return this.remainingTime > 0 ? this.remainingTime - this.count + this
							.submitTimeDetail : this.incrementCount + this.submitTimeDetail
					},
					keyCom: function() {
						var t = this;
						return function(e) {
							return 0 === t.questionData.length ? null : e ? t.questionData[t
									.currentIndex].questionInfo.questionId + t.currentIndex : t
								.questionData[t.currentIndex].questionInfo
						}
					}
				},
				methods: {
					liveTestAnswered: function() {},
					closeendAnswer: function() {
						this.isSubmit = !1, this.flag = !0;
						var t = 1 === this.examInfo.examType && this.flag ? "quiz" : "testOver";
						this.$router.push({
							path: t,
							query: this.$route.query
						})
					},
					removeCacheUserAnswer: function() {
						clearInterval(this.setIntervalTimer), this.setIntervalTimer = null, localStorage
							.removeItem("userAnswer"), localStorage.removeItem("AnswerCurrentIndex"),
							localStorage.removeItem("timerDetail"), localStorage.removeItem("isSubmit")
					},
					getCacheUserAnswer: function() {
						var t = JSON.parse(localStorage.getItem("userAnswer")),
							e = localStorage.getItem("AnswerCurrentIndex"),
							n = JSON.parse(localStorage.getItem("timerDetail"));
						(null === t || void 0 === t ? void 0 : t.length) > 0 && (this.answerData = t), e &&
							(this.currentIndex = Number(e)), n && (this.count = n.count, this
								.submitTimeDetail = n.incrementCount)
					},
					cacheUserAnswer: function() {
						var t = this;
						this.setIntervalTimer = setInterval((function() {
							var e = {
								count: t.count,
								isShowTimer: t.isShowTimer,
								incrementCount: t.submitTime
							};
							localStorage.setItem("userAnswer", JSON.stringify(t.answerData)),
								localStorage.setItem("AnswerCurrentIndex", t.currentIndex),
								localStorage.setItem("timerDetail", JSON.stringify(e))
						}), 1e4)
					},
					clickLeft: function() {
						this.$router.push({
							path: "./quiz",
							query: this.$route.query
						})
					},
					getMins: function(t) {
						return Math.ceil(t / 60)
					},
					closeAppLoading: function() {
						return Object(F["a"])(regeneratorRuntime.mark((function t() {
							return regeneratorRuntime.wrap((function(t) {
								while (1) switch (t.prev = t.next) {
									case 0:
										return t.prev = 0, t.next = 3, $[
											"a"].closeAppLoading();
									case 3:
										t.next = 8;
										break;
									case 5:
										t.prev = 5, t.t0 = t["catch"](0),
											console.log("app关闭loading出错：", t
												.t0);
									case 8:
									case "end":
										return t.stop()
								}
							}), t, null, [
								[0, 5]
							])
						})))()
					},
					getUserAnwser: function(t) {
						var e = this;
						this.answerData.length > 0 || (this.answerData = [], t.forEach((function(t) {
							var n = {
								questionId: t.questionInfo.questionId,
								questionType: t.questionInfo.questionType,
								choice: [],
								mark: !1,
								imgs: []
							};
							e.answerData.push(n)
						})))
					},
					getRemainingTime: function() {
						if (this.validEndTime && 0 !== Number(this.validEndTime)) {
							var t = (this.validEndTime - (new Date).getTime()) / 1e3;
							this.isShowTimer ? this.count = t > this.count ? this.count : t : this.count = t
						}
					},
					getData: function() {
						var t = [{
							questionInfo: {
								sitesId: "7970848046376484864",
								questionId: "56e97e4f165f42ae9eb136d49690ba5f",
								questionType: 4,
								question: "<p>123412412</p>",
								options: [],
								answers: [],
								solution: "",
								subject: "Biology",
								subjectId: "2",
								topic: [],
								mark: 300,
								deductMark: 100,
								difficultyLevel: 1,
								examType: "ICSE",
								examTypeId: "38",
								grade: "Class1",
								gradeId: "25",
								source: "Exam",
								knowledgeKids: "-",
								knowledgeNames: [],
								questionImage: [],
								solutionImage: [],
								videoInfo: [],
								questionSource: 1
							},
							seqNum: 0,
							accuracy: 0
						}, {
							questionInfo: {
								sitesId: "7970848046376484864",
								questionId: "185399d57af5493f962585a0fd1a513c",
								questionType: 5,
								question: "<p>2141234</p>",
								options: [],
								answers: [],
								solution: "",
								subject: "Biology",
								subjectId: "2",
								topic: [],
								mark: 300,
								deductMark: 100,
								difficultyLevel: 1,
								examType: "CBSE",
								examTypeId: "37",
								grade: "Class3",
								gradeId: "27",
								source: "Exam",
								knowledgeKids: "-",
								knowledgeNames: [],
								questionImage: [],
								solutionImage: [],
								videoInfo: [],
								questionSource: 1
							},
							seqNum: 0,
							accuracy: 0
						}];
						this.questionData = t
					},
					nextSubmit: function() {
						var t = this;
						this.isAllowSubmit() ? (this.notSubmitModal = !1, this.submitAnswerData = {
									questionLen: this.answerData.length,
									answereQues: 0,
									skipQues: 0
								}, this.answerData.forEach((function(e) {
									var n = e.questionType,
										s = e.imgs,
										a = e.choice,
										i = 4 === n || 5 === n || 3 === n,
										o = i && (!a[0] || "" === a[0]) && 0 === s.length,
										r = !i && 0 === a.length;
									(o || r) && t.submitAnswerData.skipQues++
								})), this.submitAnswerData.answereQues = this.submitAnswerData.questionLen -
								this.submitAnswerData.skipQues, this.submitAnswer = !0) : this
							.notSubmitModal = !0
					},
					nextFn: function() {
						this.currentIndex !== this.answerData.length - 1 && this.currentIndex < this
							.answerData.length - 1 && this.currentIndex++
					},
					previousFn: function() {
						this.currentIndex > 0 && this.currentIndex--
					},
					markFn: function() {
						this.answerData[this.currentIndex].mark = !this.answerData[this.currentIndex].mark
					},
					isAllowSubmit: function() {
						var t = this.examInfo,
							e = t.examType,
							n = t.submitMinTime,
							s = t.submitMaxTime;
						return !(1 === e && n > 0 && (n > s - this.count && this.isShowTimer || n > this
							.incrementCount && !this.isShowTimer))
					},
					rightFn: function() {
						this.isAllowSubmit() ? this.submitAnswer = !0 : this.notSubmitModal = !0
					},
					answerFn: function(t) {
						this.currentIndex = t
					},
					continueAnswer: function() {
						this.isGiveUp = !1
					},
					confirmGiveUp: function() {
						this.isGiveUp = !1, this.$router.push({
							path: "./quiz",
							query: this.$route.query
						}), this.next()
					},
					confirmSubmit: function() {
						this.sendAnswerData()
					},
					computerSendAnswerParams: function() {
						var t = {
								examId: this.examInfo.examId,
								userAnswerId: this.userAnswerId,
								answers: [],
								useTime: parseInt(this.submitTime),
								isEnd: !0
							},
							e = JSON.parse(JSON.stringify(this.answerData));
						return e.forEach((function(e) {
							var n = {
								questionId: e.questionId,
								choice: e.choice
							};
							4 !== e.questionType && 5 !== e.questionType || (n.imgs = e.imgs),
								1 === e.questionType && n.choice && (n.choice = n.choice.sort((
									function(t, e) {
										return t.charCodeAt(0) - e.charCodeAt(0)
									}))), t.answers.push(n)
						})), t
					},
					sendAnswerData: function() {
						this.showLoading = !0, this.isAppIn && this.closeAppLoading();
						var t = this.computerSendAnswerParams();
						console.log(t)
					},
					setTimerDetail: function() {
						var t = this;
						this.timer = setInterval((function() {
							t.count--;
							var e = {
								1800: "Thirty minutes are left.",
								300: "Five minutes are left."
							};
							e[parseInt(t.count)] && t.$toast(e[parseInt(t.count)]), t.count <=
								0 && (t.clearTimer(), t.count = 0, t.sendAnswerData((
							function() {
									t.isSubmit = !0
								})))
						}), 1e3)
					},
					setTimer: function() {
						var t = this;
						this.timer || (this.isShowTimer || !this.isShowTimer && 1 === Number(this.examInfo
							.examType) ? this.setTimerDetail() : this.timer = setInterval((
						function() {
							t.incrementCount++
						}), 1e3))
					},
					clearTimer: function() {
						clearInterval(this.timer), this.timer = null
					},
					pauseFn: function() {
						this.isPause = !0, this.clearTimer()
					},
					confirmPause: function() {
						this.isPause = !1, this.setTimer()
					},
					selectFn: function(t) {
						var e = this.answerData[this.currentIndex],
							n = this.questionData[this.currentIndex].questionInfo.questionType;
						switch (n) {
							case 0:
								this.$set(e.choice, 0, t);
								break;
							case 1:
								this.$set(e, "choice", t);
								break;
							case 2:
								this.$set(e.choice, 0, t);
								break;
							case 3:
								this.$set(e.choice, 0, t);
								break;
							case 4:
								this.$set(e.choice, 0, t.inputV), this.$set(e, "imgs", t.imgList);
								break;
							case 5:
								this.$set(e.choice, 0, t.inputV), this.$set(e, "imgs", t.imgList);
								break
						}
					}
				},
				watch: {
					isPause: function(t) {
						var e = this;
						t && this.$dialog({
							overlayClass: "pausedDialog",
							confirmButtonText: "Continue",
							confirmButtonColor: "#22BE8C",
							message: "The timer has been already paused. Click the button to continue."
						}).then((function() {
							e.setTimer(), e.isPause = !1
						}))
					},
					notSubmitModal: function(t) {
						t && (this.$toast("The test can only be submitted (".concat(this.getMins(this
								.examInfo.submitMinTime), " mins ) after the test begins.")), this
							.notSubmitModal = !1)
					},
					isGiveUp: function(t) {
						var e = this;
						t && this.$dialog({
							className: "quitDialog",
							confirmButtonText: "Quit",
							confirmButtonColor: "#DC143C",
							cancelButtonText: "Continue",
							showCancelButton: !0,
							message: "Are you sure you want to quit? Your answer data will not be saved. ",
							closeOnClickOverlay: !0
						}).then((function() {
							e.confirmGiveUp()
						})).catch((function() {
							e.continueAnswer()
						}))
					},
					isSubmitInfo: function(t) {
						t && this.confirmGiveUp()
					}
				},
				beforeDestroy: function() {
					this.isSubmit = !1, this.clearTimer(), this.removeCacheUserAnswer()
				},
				destroyed: function() {
					this.clearTimer(), this.removeCacheUserAnswer()
				},
				beforeRouteLeave: function(t, e, n) {
					"quiz" === t.name && this.isSubmit ? (this.next = n, this.isSubmitInfo = !0) :
						"quiz" !== t.name || this.flag || this.isShowIsAnswer ? n() : (this.isAppIn || (this
							.isGiveUp = !0, n(!1)), this.next = n)
				}
			},
			qe = Se,
			Le = (n("da4a"), n("5558"), Object(V["a"])(qe, B, N, !1, null, "7de410ca", null)),
			Oe = Le.exports,
			Me = n("2b0e"),
			Be = n("8c4f");
		Me["default"].use(Be["a"]);
		var Ne = [],
			Fe = new Be["a"]({
				mode: "history",
				routes: Ne
			}),
			Ue = Fe,
			Pe = {
				namespaced: !0,
				state: {
					appUid: "",
					appUserId: "",
					appSitesID: "",
					appWebToken: "",
					isAppIn: !1
				},
				mutations: {
					setIsAPP: function(t) {
						var e = navigator.userAgent.toLocaleLowerCase();
						e.indexOf("teacheemaster") > -1 && (t.isAppIn = !0)
					},
					setAppUid: function(t, e) {
						t.appUid = e
					},
					setAppUserId: function(t, e) {
						t.appUserId = e
					},
					setAppSitesID: function(t, e) {
						t.appSitesID = e
					},
					setAppWebToken: function(t, e) {
						t.appWebToken = e
					}
				},
				actions: {
					setbackListener: function(t, e) {
						t.commit;
						$["a"].backListener(e).then((function(t) {}))
					},
					getAppUid: function(t, e) {
						return Object(F["a"])(regeneratorRuntime.mark((function e() {
							var n, s;
							return regeneratorRuntime.wrap((function(e) {
								while (1) switch (e.prev = e.next) {
									case 0:
										return n = t.commit, e.prev = 1, e
											.next = 4, $["a"].getUid();
									case 4:
										return s = e.sent, n("setAppUid",
											s), e.abrupt("return", s);
									case 9:
										e.prev = 9, e.t0 = e["catch"](1),
											Promise.reject(e.t0);
									case 12:
									case "end":
										return e.stop()
								}
							}), e, null, [
								[1, 9]
							])
						})))()
					},
					getAppUserId: function(t, e) {
						return Object(F["a"])(regeneratorRuntime.mark((function e() {
							var n, s;
							return regeneratorRuntime.wrap((function(e) {
								while (1) switch (e.prev = e.next) {
									case 0:
										return n = t.commit, e.prev = 1, e
											.next = 4, $["a"].getUserId();
									case 4:
										return s = e.sent, n("setAppUserId",
											s), e.abrupt("return", s);
									case 9:
										e.prev = 9, e.t0 = e["catch"](1),
											Promise.reject(e.t0);
									case 12:
									case "end":
										return e.stop()
								}
							}), e, null, [
								[1, 9]
							])
						})))()
					},
					getAppSitesID: function(t, e) {
						return Object(F["a"])(regeneratorRuntime.mark((function e() {
							var n, s;
							return regeneratorRuntime.wrap((function(e) {
								while (1) switch (e.prev = e.next) {
									case 0:
										return t.getters, n = t.commit, e
											.prev = 1, e.next = 4, $["a"]
											.getSetesId();
									case 4:
										return s = e.sent, n(
												"setAppSitesID", s), e
											.abrupt("return", s);
									case 9:
										e.prev = 9, e.t0 = e["catch"](1),
											Promise.reject(e.t0);
									case 12:
									case "end":
										return e.stop()
								}
							}), e, null, [
								[1, 9]
							])
						})))()
					},
					getAppWebToken: function(t, e) {
						return Object(F["a"])(regeneratorRuntime.mark((function e() {
							var n, s;
							return regeneratorRuntime.wrap((function(e) {
								while (1) switch (e.prev = e.next) {
									case 0:
										return n = t.commit, e.prev = 1, e
											.next = 4, $["a"].getWebToken();
									case 4:
										return s = e.sent, n(
												"setAppWebToken", s), e
											.abrupt("return", s);
									case 9:
										e.prev = 9, e.t0 = e["catch"](1),
											Promise.reject(e.t0);
									case 12:
									case "end":
										return e.stop()
								}
							}), e, null, [
								[1, 9]
							])
						})))()
					},
					appAddPicture: function(t, e) {
						return Object(F["a"])(regeneratorRuntime.mark((function n() {
							var s;
							return regeneratorRuntime.wrap((function(n) {
								while (1) switch (n.prev = n.next) {
									case 0:
										return t.commit, n.prev = 1, n
											.next = 4, $["a"].appAddPicture(
												e);
									case 4:
										return s = n.sent, n.abrupt(
											"return", s);
									case 8:
										n.prev = 8, n.t0 = n["catch"](1),
											Promise.reject(n.t0);
									case 11:
									case "end":
										return n.stop()
								}
							}), n, null, [
								[1, 8]
							])
						})))()
					}
				}
			},
			Ee = {
				in: {
					singleChoice: "Single Choice",
					multipleChoice: "Multiple Choice",
					trueOrFalse: "True or False",
					numberical: "Numerical",
					fillInTheBlank: "Fill in the blank",
					subjective: "Subjective",
					score: "Score",
					penalty: "Penalty",
					enterAnswer: "Upload or enter your answer",
					questionStatus: "unchecked",
					scoreText: "Score",
					penaltyText: "Penalty"
				},
				kr: {
					singleChoice: "단일 선택",
					multipleChoice: "객관식",
					trueOrFalse: "O/X",
					numberical: "수치형",
					fillInTheBlank: "빈칸을 채우세요",
					subjective: "주관적인",
					score: "점수",
					penalty: "패널티",
					enterAnswer: "답안 입력 또는 업로드",
					questionStatus: "미확인",
					scoreText: "득점",
					penaltyText: "감점"
				}
			};
		Me["default"].use(H["a"]);
		var ze = new H["a"].Store({
				state: {
					lang: Ee,
					country: "in"
				},
				mutations: {
					changeCountry: function(t, e) {
						t.country = e
					}
				},
				actions: {},
				getters: {
					getCountry: function(t) {
						return t.country
					},
					getLang: function(t) {
						return t.lang[t.country]
					}
				},
				modules: {
					appUserStore: Pe
				}
			}),
			Ve = (n("f689"), n("5c96")),
			Re = n("91f4"),
			Ye = n.n(Re);
		n("157a");
		M["a"].config.productionTip = !1, new M["a"]({
				router: Ue,
				store: ze,
				render: function(t) {
					return t(Oe)
				}
			}).$mount("#app"), M["a"].use(O["a"]), M["a"].use(L["a"]), M["a"].use(q["a"]), M["a"].use(S["a"]),
			M["a"].use(D["a"]), M["a"].use(_["a"]), M["a"].use(T["a"]), M["a"].use(k["a"]), M["a"].use(j["a"]),
			M["a"].use(x["a"]), M["a"].use(y["a"]), M["a"].use(I["a"]), M["a"].use(C["a"]), M["a"].use(A["a"]),
			M["a"].use(w["a"]), M["a"].use(b["a"]), M["a"].use(v["a"]), M["a"].use(g["a"]), M["a"].use(h["a"]),
			M["a"].use(p["a"]), M["a"].use(f["a"]), M["a"].use(m["a"]), M["a"].use(d["a"]), M["a"].use(l["a"]),
			M["a"].use(u["a"]), M["a"].use(Ve["Upload"]), M["a"].use(c["a"]), M["a"].use(r["a"]), M["a"].use(o[
				"a"]), M["a"].use(i["a"]), M["a"].use(a["a"]), s["a"].use("en-US", Ye.a), M["a"].use(Ve[
				"Upload"])
	},
	4678: function(t, e, n) {
		var s = {
			"./af": "2bfb",
			"./af.js": "2bfb",
			"./ar": "8e73",
			"./ar-dz": "a356",
			"./ar-dz.js": "a356",
			"./ar-kw": "423e",
			"./ar-kw.js": "423e",
			"./ar-ly": "1cfd",
			"./ar-ly.js": "1cfd",
			"./ar-ma": "0a84",
			"./ar-ma.js": "0a84",
			"./ar-sa": "8230",
			"./ar-sa.js": "8230",
			"./ar-tn": "6d83",
			"./ar-tn.js": "6d83",
			"./ar.js": "8e73",
			"./az": "485c",
			"./az.js": "485c",
			"./be": "1fc1",
			"./be.js": "1fc1",
			"./bg": "84aa",
			"./bg.js": "84aa",
			"./bm": "a7fa",
			"./bm.js": "a7fa",
			"./bn": "9043",
			"./bn-bd": "9686",
			"./bn-bd.js": "9686",
			"./bn.js": "9043",
			"./bo": "d26a",
			"./bo.js": "d26a",
			"./br": "6887",
			"./br.js": "6887",
			"./bs": "2554",
			"./bs.js": "2554",
			"./ca": "d716",
			"./ca.js": "d716",
			"./cs": "3c0d",
			"./cs.js": "3c0d",
			"./cv": "03ec",
			"./cv.js": "03ec",
			"./cy": "9797",
			"./cy.js": "9797",
			"./da": "0f14",
			"./da.js": "0f14",
			"./de": "b469",
			"./de-at": "b3eb",
			"./de-at.js": "b3eb",
			"./de-ch": "bb71",
			"./de-ch.js": "bb71",
			"./de.js": "b469",
			"./dv": "598a",
			"./dv.js": "598a",
			"./el": "8d47",
			"./el.js": "8d47",
			"./en-au": "0e6b",
			"./en-au.js": "0e6b",
			"./en-ca": "3886",
			"./en-ca.js": "3886",
			"./en-gb": "39a6",
			"./en-gb.js": "39a6",
			"./en-ie": "e1d3",
			"./en-ie.js": "e1d3",
			"./en-il": "7333",
			"./en-il.js": "7333",
			"./en-in": "ec2e",
			"./en-in.js": "ec2e",
			"./en-nz": "6f50",
			"./en-nz.js": "6f50",
			"./en-sg": "b7e9",
			"./en-sg.js": "b7e9",
			"./eo": "65db",
			"./eo.js": "65db",
			"./es": "898b",
			"./es-do": "0a3c",
			"./es-do.js": "0a3c",
			"./es-mx": "b5b7",
			"./es-mx.js": "b5b7",
			"./es-us": "55c9",
			"./es-us.js": "55c9",
			"./es.js": "898b",
			"./et": "ec18",
			"./et.js": "ec18",
			"./eu": "0ff2",
			"./eu.js": "0ff2",
			"./fa": "8df4",
			"./fa.js": "8df4",
			"./fi": "81e9",
			"./fi.js": "81e9",
			"./fil": "d69a",
			"./fil.js": "d69a",
			"./fo": "0721",
			"./fo.js": "0721",
			"./fr": "9f26",
			"./fr-ca": "d9f8",
			"./fr-ca.js": "d9f8",
			"./fr-ch": "0e49",
			"./fr-ch.js": "0e49",
			"./fr.js": "9f26",
			"./fy": "7118",
			"./fy.js": "7118",
			"./ga": "5120",
			"./ga.js": "5120",
			"./gd": "f6b4",
			"./gd.js": "f6b4",
			"./gl": "8840",
			"./gl.js": "8840",
			"./gom-deva": "aaf2",
			"./gom-deva.js": "aaf2",
			"./gom-latn": "0caa",
			"./gom-latn.js": "0caa",
			"./gu": "e0c5",
			"./gu.js": "e0c5",
			"./he": "c7aa",
			"./he.js": "c7aa",
			"./hi": "dc4d",
			"./hi.js": "dc4d",
			"./hr": "4ba9",
			"./hr.js": "4ba9",
			"./hu": "5b14",
			"./hu.js": "5b14",
			"./hy-am": "d6b6",
			"./hy-am.js": "d6b6",
			"./id": "5038",
			"./id.js": "5038",
			"./is": "0558",
			"./is.js": "0558",
			"./it": "6e98",
			"./it-ch": "6f12",
			"./it-ch.js": "6f12",
			"./it.js": "6e98",
			"./ja": "079e",
			"./ja.js": "079e",
			"./jv": "b540",
			"./jv.js": "b540",
			"./ka": "201b",
			"./ka.js": "201b",
			"./kk": "6d79",
			"./kk.js": "6d79",
			"./km": "e81d",
			"./km.js": "e81d",
			"./kn": "3e92",
			"./kn.js": "3e92",
			"./ko": "22f8",
			"./ko.js": "22f8",
			"./ku": "2421",
			"./ku.js": "2421",
			"./ky": "9609",
			"./ky.js": "9609",
			"./lb": "440c",
			"./lb.js": "440c",
			"./lo": "b29d",
			"./lo.js": "b29d",
			"./lt": "26f9",
			"./lt.js": "26f9",
			"./lv": "b97c",
			"./lv.js": "b97c",
			"./me": "293c",
			"./me.js": "293c",
			"./mi": "688b",
			"./mi.js": "688b",
			"./mk": "6909",
			"./mk.js": "6909",
			"./ml": "02fb",
			"./ml.js": "02fb",
			"./mn": "958b",
			"./mn.js": "958b",
			"./mr": "39bd",
			"./mr.js": "39bd",
			"./ms": "ebe4",
			"./ms-my": "6403",
			"./ms-my.js": "6403",
			"./ms.js": "ebe4",
			"./mt": "1b45",
			"./mt.js": "1b45",
			"./my": "8689",
			"./my.js": "8689",
			"./nb": "6ce3",
			"./nb.js": "6ce3",
			"./ne": "3a39",
			"./ne.js": "3a39",
			"./nl": "facd",
			"./nl-be": "db29",
			"./nl-be.js": "db29",
			"./nl.js": "facd",
			"./nn": "b84c",
			"./nn.js": "b84c",
			"./oc-lnc": "167b",
			"./oc-lnc.js": "167b",
			"./pa-in": "f3ff",
			"./pa-in.js": "f3ff",
			"./pl": "8d57",
			"./pl.js": "8d57",
			"./pt": "f260",
			"./pt-br": "d2d4",
			"./pt-br.js": "d2d4",
			"./pt.js": "f260",
			"./ro": "972c",
			"./ro.js": "972c",
			"./ru": "957c",
			"./ru.js": "957c",
			"./sd": "6784",
			"./sd.js": "6784",
			"./se": "ffff",
			"./se.js": "ffff",
			"./si": "eda5",
			"./si.js": "eda5",
			"./sk": "7be6",
			"./sk.js": "7be6",
			"./sl": "8155",
			"./sl.js": "8155",
			"./sq": "c8f3",
			"./sq.js": "c8f3",
			"./sr": "cf1e",
			"./sr-cyrl": "13e9",
			"./sr-cyrl.js": "13e9",
			"./sr.js": "cf1e",
			"./ss": "52bd",
			"./ss.js": "52bd",
			"./sv": "5fbd",
			"./sv.js": "5fbd",
			"./sw": "74dc",
			"./sw.js": "74dc",
			"./ta": "3de5",
			"./ta.js": "3de5",
			"./te": "5cbb",
			"./te.js": "5cbb",
			"./tet": "576c",
			"./tet.js": "576c",
			"./tg": "3b1b",
			"./tg.js": "3b1b",
			"./th": "10e8",
			"./th.js": "10e8",
			"./tk": "5aff",
			"./tk.js": "5aff",
			"./tl-ph": "0f38",
			"./tl-ph.js": "0f38",
			"./tlh": "cf75",
			"./tlh.js": "cf75",
			"./tr": "0e81",
			"./tr.js": "0e81",
			"./tzl": "cf51",
			"./tzl.js": "cf51",
			"./tzm": "c109",
			"./tzm-latn": "b53d",
			"./tzm-latn.js": "b53d",
			"./tzm.js": "c109",
			"./ug-cn": "6117",
			"./ug-cn.js": "6117",
			"./uk": "ada2",
			"./uk.js": "ada2",
			"./ur": "5294",
			"./ur.js": "5294",
			"./uz": "2e8c",
			"./uz-latn": "010e",
			"./uz-latn.js": "010e",
			"./uz.js": "2e8c",
			"./vi": "2921",
			"./vi.js": "2921",
			"./x-pseudo": "fd7e",
			"./x-pseudo.js": "fd7e",
			"./yo": "7f33",
			"./yo.js": "7f33",
			"./zh-cn": "5c3a",
			"./zh-cn.js": "5c3a",
			"./zh-hk": "49ab",
			"./zh-hk.js": "49ab",
			"./zh-mo": "3a6c",
			"./zh-mo.js": "3a6c",
			"./zh-tw": "90ea",
			"./zh-tw.js": "90ea"
		};

		function a(t) {
			var e = i(t);
			return n(e)
		}

		function i(t) {
			if (!n.o(s, t)) {
				var e = new Error("Cannot find module '" + t + "'");
				throw e.code = "MODULE_NOT_FOUND", e
			}
			return s[t]
		}
		a.keys = function() {
			return Object.keys(s)
		}, a.resolve = i, t.exports = a, a.id = "4678"
	},
	"4f1e": function(t, e, n) {
		"use strict";
		n("1faf")
	},
	5412: function(t, e, n) {
		"use strict";
		n("0b8a")
	},
	5558: function(t, e, n) {
		"use strict";
		n("a3e5")
	},
	5720: function(t, e, n) {
		"use strict";
		n("a916")
	},
	"58a8": function(t, e, n) {},
	"59e1": function(t, e) {
		t.exports =
			"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAcCAYAAAByDd+UAAAACXBIWXMAABYlAAAWJQFJUiTwAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAEJSURBVHgB7Za9bcNADIVJHdXLGygbBOpTXOHCpSfICkEGCIIMkGyQIBMEqVy4uCJ9vIImsNy4Es80CfgaN7YByW7uAQTvB+AHks1DUIUQamb6QoR7EahgYGnNn7LkZ+99iwaLkVZjgI7UEXFDfU8f2lklIt/bbXyaz/0GBtJiESzVzrlXRHzUKX7icvkn9srMd7OZb2Fg6QQtTRS21twV6WMMmEn3ps1QmlpVwJWVgRmYgRmYgRmYgbcEHrzHVYDJb0zGgFpNtYd1upPGSsPHWL4717+pi2tPFZlOHwTOUHJsMboXO+x28ovqHWsi+rcPGFXSMcemMHuonrQxO26vZ8YlIFtZMJix9gy8eAVwUncKAAAAAElFTkSuQmCC"
	},
	"5c97": function(t, e, n) {},
	"67fb": function(t, e, n) {},
	"68b2": function(t, e, n) {
		"use strict";
		n("0f4f")
	},
	"76bb": function(t, e, n) {},
	7828: function(t, e, n) {
		"use strict";
		n("2491")
	},
	7929: function(t, e, n) {},
	"815f": function(t, e, n) {},
	a3e5: function(t, e, n) {},
	a4f3: function(t, e, n) {
		"use strict";
		n("24ca")
	},
	a541: function(t, e, n) {
		"use strict";
		n("76bb")
	},
	a916: function(t, e, n) {},
	b0f4: function(t, e, n) {},
	b87a: function(t, e, n) {
		"use strict";
		n("b0f4")
	},
	ba96: function(t, e, n) {
		"use strict";
		n("7929")
	},
	bcce: function(t, e, n) {
		"use strict";
		n("c61e")
	},
	c61e: function(t, e, n) {},
	c8a2: function(t, e) {
		t.exports =
			"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAIqSURBVHgBrVRdUuJAEJ4e4jtHCCdYfFeLVEEsn5ATKCcATgCeQD2BeALZJzXRisC+L3uCzd4gVT5uMrNfJxPLDQPLWnZVZyadnm++9B8Ji0RRVM8y50wp0ZJSN7Um13xaaS1iKWnWbh/c2s5S1fD4uDit1egGB+tiixCJGM9JFVi+fwnDxSVuvzNgLzg0TFOn0ekcEiuY7mut+9AYPi7WaRgux1aGz8/LMX5xIoROcM9Fp3NwJbZIGM6H8GMwXE5X8B+9AQbB4pyIbhhM68zzfW8ldpAg+NYk0hGDKqV7x8dHsxzw6Wn5k38B+KN/MdvA9BLbxHHSBpXsOC6+f9QQHxDEkVm2skyNkBTqslFKObE546IB9LvRMzuk+spPEEOZoc5yk/r9wwJ2joVD0DQ6hW1Y9UtTNeMV5fZFlkW7IREDi22N5cmJFxcEhCvFJ4tE2mPe3N9HruW7rb3WqiAIoqbZrqRSlP8qkTytOiLIfLjPjkZHsFkuqZk86F9gqOa5qSa7wiIAmEL3jVprFO06Nhh30nHUFHu0m2g9PMyH4j8FTTEwfR3zoJCe5yWIY9/cMOZ22hUMY459JwXLoo7zLLfb6EES19jWuTd3YcrMMDMjnkwYKhflGPtrHqKFJljyeJTzjgu+rFGuhL09pwsQTmCLbdhf+/7hG4G1Acu9zaBIgCu2ik7ArM8T5r2VNrkXwNQFU1cUbSe4ZrnMpBQvr6/pba/nJdVzfwC3Mwg8kK+clwAAAABJRU5ErkJggg=="
	},
	cc73: function(t, e) {
		t.exports =
			"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAGrSURBVHgBrVTNSgJRFP7OdaBW0bKgYBJaREHREyS1aNffA6RvUCS0CbRlf5hPoD6A6QNk1gMkbmqXTRhYO4k2Qc7pzPiTNTNWTt9m7p3z3e+ee/4ILtCLiWGFxiaZWGCiOYD1lqlMDAMK+btQNON2lhxi5werSgVSshxGT7BBRPHvwqp7EyyeJEQs97OY7YvOjHSweBxz9dA2MOLoB0ynlcWd7Y6gXjgMK1Ip+IBpYs1YiuZtwYmLo3sC6fCHukmBiUDLuzD8Y5DYfNYIaqUXKzI2j/XRGXudrl4j+3TjyWXCgoZmnbkSNkamsTcZ6uwPp5YxpA0g9Vhy5Ytzs4o+i9aB8Pi885LRaXiDdYV/hmKQ4WU8qznjlaqW4A0ua2Auy+N1N7MVq5f3t87Ts7Xb3kkBHiTLfCXhXPUiWQK9RLqhiHLKVFpa1nX4BhvWoFBGaLsubROBT1iTx/raWbZ6UAKQRL9g3m+PsS/zMFg4istVsb+JIVlZjG61t84BK70t7sd+MSwkVI2IsbSb7/5JXmxb2OpzKSkhzTWdkZqVMiOFSxOvGSMUdyTzA+uvk6afs8tWAAAAAElFTkSuQmCC"
	},
	d413: function(t, e, n) {},
	da4a: function(t, e, n) {
		"use strict";
		n("67fb")
	},
	ecff: function(t, e, n) {
		"use strict";
		n("d413")
	},
	f3c4: function(t, e, n) {
		"use strict";
		n("1691")
	},
	f689: function(t, e, n) {},
	fc0d: function(t, e, n) {
		"use strict";
		n("d3b7"), n("a9e3"), n("4add");
		var s = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("data", "webToken", {}, (function(e) {
							return t(e)
						}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			a = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("data", "uid", {}, (function(e) {
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
						window.YYApiCore.invokeClientMethod("data", "userId", {}, (function(e) {
							return t(e)
						}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			o = function(t) {
				return new Promise((function(e, n) {
					try {
						window.YYApiCore.invokeClientMethod("data", "showImg", {
							url: t
						}, (function(t) {
							return e(t)
						}))
					} catch (s) {
						return n(s)
					}
				}))
			},
			r = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("data", "instituteId", {}, (function(
						e) {
							return t(e)
						}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			c = function(t) {
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
						}, (function(n) {
							"true" === t.enabled && Number(n.id) === Number(t.id) && t
								.bc(), e(n)
						}))
					} catch (n) {
						return e(0)
					}
				}))
			},
			u = function() {
				try {
					window.YYApiCore.invokeClientMethod("ui", "popViewController")
				} catch (t) {
					console.log(t)
				}
			},
			l = function(t) {
				return new Promise((function(e, n) {
					try {
						window.YYApiCore.invokeClientMethod("data", "addPicture", {
							pictureNum: t
						}, (function(t) {
							t && t.picture && e(t)
						}))
					} catch (s) {
						return n(s)
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
			m = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("data", "deviceInfo", {}, (function(e) {
							t(e)
						}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			f = function(t) {
				return new Promise((function(e, n) {
					try {
						window.YYApiCore.invokeClientMethod("data", "isSupport", {
							functionName: t
						}, (function(t) {
							e(t)
						}))
					} catch (s) {
						return n(s)
					}
				}))
			},
			p = function() {
				return new Promise((function(t, e) {
					try {
						window.YYApiCore.invokeClientMethod("ui", "popViewController", {}, (
							function(e) {
								t(e)
							}))
					} catch (n) {
						return e(n)
					}
				}))
			},
			h = function(t) {
				return new Promise((function(e, n) {
					try {
						window.YYApiCore.invokeClientMethod("data", "playVideo", {
							url: t
						}, (function(t) {
							e(t)
						}))
					} catch (s) {
						return n(s)
					}
				}))
			};
		e["a"] = {
			getWebToken: s,
			getUid: a,
			getUserId: i,
			getSetesId: r,
			backListener: c,
			giveupH5: u,
			appAddPicture: l,
			closeAppLoading: d,
			getAppDeviceInfo: m,
			getAppIsSupMethods: f,
			setPopViewController: p,
			setNativeVideo: h,
			showImg: o
		}
	}
});
