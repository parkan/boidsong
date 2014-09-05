(defproject boids "1.0.0-SNAPSHOT"
  :description "FIXME: write"
  :dependencies [[org.clojure/clojure "1.5.1"],
                 [lein-light-nrepl "0.0.10"]
                 [overtone "0.9.1"]]
  :repl-options {:nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]})
