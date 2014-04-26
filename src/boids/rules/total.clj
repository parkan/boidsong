(ns boids.rules.total
  (:use boids.spatial-vector
	boids.rules.center-of-mass
	boids.rules.goal
	boids.rules.velocity
	boids.rules.avoidance
	boids.rules.bounds))

(def ^:dynamic *center-of-mass-weight* 0.01)
(def ^:dynamic *velocity-weight* 1.0)
(def ^:dynamic *avoidance-weight* 1.0)
(def ^:dynamic *bounds-weight* 1.0)
(def ^:dynamic *goal-weight* 1.0)
(def ^:dynamic *bounds-radius* 0.0)
(def ^:dynamic *avoidance-radius* 2.0)

(defn total-adjustment
  ([the-boid all-boids bounds]
     (let [c (sv-mul *center-of-mass-weight* (center-of-mass-adjustment the-boid all-boids))
	   v (sv-mul *velocity-weight* (velocity-adjustment the-boid all-boids))
	   a (sv-mul *avoidance-weight*
		     (avoidance-adjustment the-boid all-boids *avoidance-radius*))
	   b (sv-mul *bounds-weight* (bounds-adjustment the-boid bounds *bounds-radius*))]
       (sv-sum c v a b (:velocity the-boid))))
  ([the-boid all-boids bounds the-goal]
     (let [c (sv-mul *center-of-mass-weight* (center-of-mass-adjustment the-boid all-boids))
	   v (sv-mul *velocity-weight* (velocity-adjustment the-boid all-boids))
	   a (sv-mul *avoidance-weight*
		     (avoidance-adjustment the-boid all-boids *avoidance-radius*))
	   b (sv-mul *bounds-weight* (bounds-adjustment the-boid bounds *bounds-radius*))
	   g (sv-mul *goal-weight* (goal-adjustment the-boid the-goal))]
    (sv-sum c v a b g (:velocity the-boid)))))
