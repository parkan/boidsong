(ns boids.move-boid
  (:use boids.boid
	boids.spatial-vector
	boids.rules.total
  overtone.live
  overtone.studio.scope))

(defn move-boid-one-step [the-boid all-boids the-bounds the-goal]
     (let [adjustment (total-adjustment the-boid all-boids the-bounds the-goal)
      new-location (sv-sum (:location the-boid) adjustment)
      ]
       ; update synth params here
       ;(print (:location the-boid))
       (ctl (:inst (:synth the-boid)) :freq (:x new-location))
       (struct boid new-location adjustment (:synth the-boid))))

(defn move-all-boids-one-step [boid-list the-bounds the-goal]
     (for [b boid-list] (move-boid-one-step b boid-list the-bounds the-goal)))
