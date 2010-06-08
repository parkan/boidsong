(ns boids.boid
  (:use boids.spatial-vector
	boids.rules.total))

(defstruct boid :location :velocity)

(defn new-boid [x y vx vy]
  (struct boid (struct spatial-vector x y) (struct spatial-vector vx vy)))

(defn distance-between-boids [boid-1 boid-2]
  (distance-between (:location boid-1) (:location boid-2)))

(defn boids-in-radius-of-boid [blist the-boid radius]
  (filter #(<= (distance-between (:location the-boid) (:location %)) radius) blist))

(defn move-boid-one-step [the-boid all-boids the-bounds the-goal]
     (let [adjustment (total-adjustment the-boid all-boids the-bounds the-goal)]
       (struct boid (sv-sum (:location the-boid) adjustment) adjustment)))
	  
(defn move-all-boids-one-step [boid-list the-bounds the-goal]
     (for [b boid-list] (move-boid-one-step b boid-list the-bounds the-goal)))
