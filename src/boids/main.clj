(ns boids.main
  (:use boids.boid
	boids.bounds
	boids.graphics.draw-boids
	boids.move-boid
	boids.random
	boids.rules.total)
  (:import  (java.awt Dimension)
	    (javax.swing JFrame)
	    (javax.swing JPanel)))

(def drawable-bounds (struct bounds -500 500 -350 350))

(defn- move-boids [boids-a the-goal]
     (binding [*velocity-weight* 0.125
	       *bounds-radius* 20
	       *bounds-weight* 0.8
	       *avoidance-radius* 30.0
	       *center-of-mass-weight* 0.001
	       *avoidance-weight* 0.1
	       *goal-weight* 0.000]
       (swap! boids-a move-all-boids-one-step drawable-bounds the-goal)))

(defn- animate-flock [boid-space-agent]
  (loop []
    (move-boids (:boids-a @boid-space-agent) {:x 100 :y 300})
    (. (:panel @boid-space-agent) (repaint))
    (. Thread (sleep 50))
    (if (not (:stopped @boid-space-agent))
      (recur)
      boid-space-agent)))

(defn- start [boid-space-agent]
  {:boids-a (:boids-a boid-space-agent),
   :panel (:panel boid-space-agent),
   :frame (:frame boid-space-agent),
   :stopped false})

(defn- stop [boid-space-agent]
  {:boids-a (:boids-a boid-space-agent),
   :panel (:panel boid-space-agent),
   :frame (:frame boid-space-agent),
   :stopped true})

(defn init-flock []
  (let [d (new Dimension
	       (- (:xmax drawable-bounds) (:xmin drawable-bounds))
	       (- (:ymax drawable-bounds) (:ymin drawable-bounds)))
	boids-a (atom (random-boids 16 drawable-bounds 10.0))
	p (doto (proxy [JPanel] []
		  (paint [g] (render-boids drawable-bounds boids-a g)))
	    (.setPreferredSize d))
	f (doto (new JFrame) (.add p) .pack .show)
  the-goal {:x 100 :y 300} ]
    (agent (agent {:boids-a boids-a :panel p, :frame f, :goal the-goal :stopped true}))))

(defn start-flock [boid-space-agent]
  (send @boid-space-agent start)
  (send boid-space-agent animate-flock))

(defn stop-flock [boid-space-agent]
  (send @boid-space-agent stop))

(defn kill-flock [boid-space-agent]
  (send @boid-space-agent #(. (:frame %) (dispose))))
