(ns lambda.util.async.macros)

(defmacro go>!
  "Create and return a channel.
   body is placed in a go block, when executed the final value of the body is put! on the channel.
   If anything is thrown put that on the channel."
  [& body]
  (let [result-channel (gensym 'result-chan)]
    `(let [~result-channel (cljs.core.async/chan)]
       (cljs.core.async/go
         (try
           (if-let [result (do ~@body)]
             (cljs.core.async/>! ~result-channel result)
             (throw (js/Error. "No Value returned in go>! block")))
           (catch js/Error ~'eErr
             (println ~'eErr)
             (cljs.core.async/>! ~result-channel ~'eErr))
           (catch :default ~'eObj
             (println ~'eObj)
             (cljs.core.async/>! ~result-channel (js/Error. ~'eObj)))))
       ~result-channel)))

(defmacro <? [ch]
  `(lambda.util.async/throw-if-error (cljs.core.async/<! ~ch)))