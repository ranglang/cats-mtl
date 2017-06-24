package cats
package mtl
package laws

import cats.syntax.cartesian._
import cats.syntax.functor._

trait ApplicativeAskLaws[F[_], E] {
  implicit val ask: ApplicativeAsk[F, E]
  implicit val applicative: Applicative[F] = ask.applicative

  // external laws
  def askAddsNoEffectsAndIsNotAffected[A](fa: F[A]): IsEq[F[A]] = {
    (ApplicativeAsk.ask *> fa) <-> fa
  }

  def askIsNotAffected[A](fa: F[A]): IsEq[F[E]] = {
    (fa *> ApplicativeAsk.ask) <-> (ApplicativeAsk.ask <* fa)
  }

  // internal laws
  def readerIsAskAndMap[A](f: E => A): IsEq[F[A]] = {
    ApplicativeAsk.ask.map(f) <-> ApplicativeAsk.reader(f)
  }
}
